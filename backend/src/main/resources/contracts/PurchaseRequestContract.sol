//SPDX-License-Identifier: MIT
pragma solidity 0.8.6;

import "./BaseContract.sol";
import "./EventContract.sol";
import "./EventResellerContract.sol";
 
contract PurchaseRequestContract is BaseContract{
  enum Status{INIT,FAILED,SUCCESS}

  address public ticketBuyer;
  address public ticketEvent;
  address public ticketReseller;
  string public description;
  string public response;
  uint public requestId;
  uint public tokenId;
  Status public status;

  constructor(address _eventReseller,string memory _description) validString(_description){
    ticketBuyer = msg.sender;
    EventResellerContract er = EventResellerContract(_eventReseller);
    ticketEvent = er.targetEvent();
    ticketReseller = er.reseller();
    description = _description;
    status = Status.INIT;
  }
  
  function addRequest() external{
    require(ticketBuyer==msg.sender && status == Status.INIT,'Not valid addRequest!');
    EventContract ec = EventContract(ticketEvent);
    requestId = ec.addPurchaseRequest();
  }
  
  function successRequest() external{
    require(status==Status.INIT,'Not valid successRequest'); 
    EventContract ec = EventContract(ticketEvent);
    tokenId = ec.addTicket(msg.sender);
    ec.responseRequest();
    response = 'NFTicket Created';
    status = Status.SUCCESS;
  }
  
  function failedRequest(string memory _response) external validString(_response){
    require(ticketReseller==msg.sender && status==Status.INIT,'Not valid failedRequest!'); 
    EventContract ec = EventContract(ticketEvent);
    ec.responseRequest();
    response = _response;
    status = Status.FAILED;
  }
  
}