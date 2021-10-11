//SPDX-License-Identifier: MIT
pragma solidity 0.8.6;

contract EventResellerContract{
    
  address public targetEvent;
  address public reseller;

  constructor(address _event){
    targetEvent = _event;
    reseller = msg.sender;
  }
  
}