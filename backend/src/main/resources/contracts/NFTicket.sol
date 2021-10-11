//SPDX-License-Identifier: MIT
pragma solidity 0.8.6;

import "./BaseContract.sol";
import "./PurchaseRequestContract.sol";

contract NFTicket is BaseContract{

  address public purchaseRequest;
  uint public price;
  string public taxSeal;
  string public ticketType;
  string public uri;

  constructor(address _purchaseRequest,string memory _type,uint _price,string memory _taxSeal,string memory _uri) 
  validUint(_price) validString(_taxSeal) validString(_type){
    purchaseRequest = _purchaseRequest;
    validCaller();
    price = _price;
    taxSeal = _taxSeal;
    ticketType = _type;
    uri = _uri;
  }
  
  function addTicket() external{
    validCaller();
    PurchaseRequestContract pr = PurchaseRequestContract(purchaseRequest);
    pr.successRequest();
  }
  
  
  function validCaller() private view{
    PurchaseRequestContract pr = PurchaseRequestContract(purchaseRequest);
    require(pr.ticketReseller()==msg.sender,'Not valid ticket reseller');
  }
  
}