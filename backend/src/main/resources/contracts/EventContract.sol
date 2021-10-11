//SPDX-License-Identifier: MIT
pragma solidity 0.8.6;

import "https://github.com/0xcert/ethereum-erc721/src/contracts/tokens/nf-token-metadata.sol";
import "https://github.com/0xcert/ethereum-erc721/src/contracts/ownership/ownable.sol";
import "./BaseContract.sol";
import "./EventResellerContract.sol";
import "./NFTicket.sol";
import "./PurchaseRequestContract.sol";
 
contract EventContract is BaseContract,NFTokenMetadata,Ownable {
  enum Status{OPENED,CLOSED,DELETED}
  
  string public title;
  uint public start;
  uint public end;
  string public location;
  Status public status;
  uint public ticketCount;
  uint public requestCount;
  mapping (address=>uint) public nextResellerReqIds;
  mapping (address=>bool) public request;
  mapping (uint=>address) public requestAddress;
  mapping (address=>uint[]) public resellerRequests;
  mapping (uint=>address) public tickets;
  mapping (address=>bool) public ticketResellers;
 
  constructor(string memory _title,uint _start,uint _end,string memory _location) 
  validString(_location) validStartEnd(_start,_end) validString(_title){
      string memory hashName = toHex(keccak256(abi.encodePacked(address(this))));
      nftName = string(abi.encodePacked("Event Ticket NFT ",hashName));
      nftSymbol = string(abi.encodePacked("NFT_",hashName));
      setEvent(_title,_start,_end,_location);
      status = Status.OPENED;
      ticketCount = 0;
      requestCount = 0;
  }
  
  function addTicket(address _ticket) external validResponseRequest returns (uint){
      PurchaseRequestContract pr = PurchaseRequestContract(msg.sender);
      require(pr.tokenId()==0,'Not valid addTicket!');
      ticketCount = ticketCount + 1;
      tickets[ticketCount] = _ticket;
      super._mint(pr.ticketBuyer(), ticketCount);
      NFTicket tk = NFTicket(_ticket);
      super._setTokenUri(ticketCount, tk.uri());
      return ticketCount;
  }
  
  function addTicketReseller(address _eventReseller) external editable onlyOwner{
      EventResellerContract er = EventResellerContract(_eventReseller);
      require(er.targetEvent()==address(this) &&
              !ticketResellers[er.reseller()],'addTicketReseller error');
      ticketResellers[er.reseller()]=true;
  }

  function addPurchaseRequest() external editable returns (uint){
      PurchaseRequestContract pr = PurchaseRequestContract(msg.sender);
      require(!request[pr.ticketBuyer()] && ticketResellers[pr.ticketReseller()],'Not valid purchase request');
      request[pr.ticketBuyer()]=true;
      requestCount = requestCount + 1;
      requestAddress[requestCount] = msg.sender;
      if (nextResellerReqIds[pr.ticketReseller()]==0) {
          nextResellerReqIds[pr.ticketReseller()] = 1;
      }
      resellerRequests[pr.ticketReseller()].push(requestCount);
      return requestCount;
  }
  
  function closeEvent() 
  external onlyOwner editable{
      status = Status.CLOSED;
  }
  
  function deleteEvent() 
  external onlyOwner editable {
      require(requestCount==0,'There is already a request for this event');
      status = Status.DELETED;
  }
  
  function responseRequest() public validResponseRequest{
      PurchaseRequestContract pr = PurchaseRequestContract(msg.sender);
      request[pr.ticketBuyer()]=false;
      nextResellerReqIds[pr.ticketReseller()] +=1;
  }
  
  function setEvent(string memory _title,uint _start,uint _end,string memory _location) private{
      title = _title;
      start = _start;
      end = _end;
      location = _location;
  }
  function updateEvent(string memory _title,uint _start,uint _end,string memory _location) 
  external onlyOwner editable validString(_location) validStartEnd(_start,_end) validString(_title){
      setEvent(_title,_start,_end,_location);
  }
  modifier editable(){
      require(status==Status.OPENED,'Event not opened!');
      _;
  }
  modifier validResponseRequest(){
      PurchaseRequestContract pr = PurchaseRequestContract(msg.sender);
      require(request[pr.ticketBuyer()] &&
              requestAddress[resellerRequests[pr.ticketReseller()][nextResellerReqIds[pr.ticketReseller()]-1]]==msg.sender,
              'Not valid response request');
      _;
  }
}