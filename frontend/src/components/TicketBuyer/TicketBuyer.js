import React from 'react'
import {
    Card, CardText, CardBody,
    CardTitle, CardSubtitle, Button
  } from 'reactstrap';

import Web3 from 'web3';

  
const emoji = require("emoji-dictionary");


/*
const web3 = new Web3(Web3.givenProvider || "http://localhost:8081");
web3.eth.getAccounts().then(console.log);


function onClickTestWeb3Javascript() {		    
  $.getJSON('EventContract.abi').done(
    function(data) {
     const EventContract = new web3.eth.Contract(data, event.id);
     $.getJSON('PurchaseRequestContract.abi').done(function(data2) {
      const PurchaseRequestContract = new web3.eth.Contract(data2, purchaseRequest.id);
      var account = web3.eth.accounts.privateKeyToAccount(document.getElementById('tb_private_key').value);
      var tBAddress = account.address;
      PurchaseRequestContract.methods.tokenId().call({from: tBAddress}, function(error, result){
        EventContract.methods.tickets(result).call({from: tBAddress}, function(error2, result2){
           console.log(result2);
         });
        });				   
    });
  });
  }
*/

class TicketBuyer extends React.Component {

    seeEvents(){
      window.location.href = "/ticketBuyer/events"
    }
    

    render(){
        return(
            <div>
        <Card>
          <CardBody>
            <CardTitle tag="h5">
              <img className='google-photo' src={sessionStorage.getItem("foto_utente")} alt = "" />
              Ciao {sessionStorage.getItem("nome_utente")}! Hai la qualifica di Ticket Buyer.
            </CardTitle>
            <CardSubtitle tag="h6" className="mb-2 text-muted">E' un piacere vederti su EventChain!</CardSubtitle>
            <CardText style={{marginTop: '20px'}}>
               {emoji.getUnicode("bust_in_silhouette")}: {sessionStorage.getItem("fullname_utente")}{"\n"}
            </CardText>
            <CardText>
               {emoji.getUnicode('e-mail')}: {sessionStorage.getItem("email_utente")}
            </CardText>
            <CardText>
              I tuoi permessi: <br />
              {emoji.getUnicode("white_check_mark")} :Ticket Buyer <br />
              {emoji.getUnicode("x")} :Event Manager<br />
              {emoji.getUnicode("x")} :Ticker Reseller<br />
            </CardText>

          <Button className="button-color" color="primary" 
              onClick={ this.seeEvents } >
                Visualizza gli eventi
          </Button> 

          <Button className="button-color" color="primary" 
              onClick={ this.onClickTestWeb3Javascript } >
                Testa Web3JS
          </Button> 



          <a href="/" className="btn btn-primary button-color">Logout</a>
          </CardBody>

          
        </Card>
        </div>
        )
    }
}

export default TicketBuyer;