import React from 'react'
import { Button ,Card, CardText, CardBody, CardTitle } from 'reactstrap';
import Web3 from 'web3';


import ticketPic from '../../images/ticket2.jpg'

import $ from 'jquery'; 


class BuyerPurchaseRequests extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          isLoaded: false,

          items: [],
          failed_purchase: [],
          chiave: ''
        };
      }

      onClickTestWeb3Javascript = (eventId, requestId, chiaveUtente) => {		   
        
       //const fs = require('fs');

        const web3 = new Web3(Web3.givenProvider || "http://localhost:7545");
        
        // Funziona:
        // web3.eth.getAccounts().then(alert);

        /*
        const contractJson = fs.readFileSync('./EventContract.abi');
        const evabi = JSON.parse(contractJson);
        const EventContract = new web3.eth.Contract(evabi, eventId);

        const purabi = JSON.parse(PurchaseAbi);
        const PurchaseRequestContract = new web3.eth.Contract(purabi, requestId);
        
        var account = web3.eth.accounts.privateKeyToAccount('828f60e450946475b88a7d8bf5b0290040399cad177acc993c95ee83d4d04a6e');
        var tBAddress = account.address;

        PurchaseRequestContract.methods.tokenId().call({from: tBAddress}, function(error, result){
          
          EventContract.methods.ownerOf(result)
						.call({from: tBAddress}, function(error2, result2){
						   alert(result2);
			 			});

          EventContract.methods.tickets(result).call({from: tBAddress}, function(error2, result2){
            alert(result2);
          });
        });				   
       }
        */

      
        $.getJSON('../../../EventContract.abi').done(
          function(data) {
           const EventContract = new web3.eth.Contract(data, eventId);
           $.getJSON('../../../PurchaseRequestContract.abi').done(function(data2) {
            const PurchaseRequestContract = new web3.eth.Contract(data2, requestId);
            var account = web3.eth.accounts.privateKeyToAccount(chiaveUtente);
            var tBAddress = account.address;
            PurchaseRequestContract.methods.tokenId().call({from: tBAddress}, function(error, result){
              
              EventContract.methods.ownerOf(result)
              .call({from: tBAddress}, function(error2, result2){
                 alert("Proprietario del biglietto: "+result2);
               });
               
              /*
              Indirizzo del NFTicket contract
              EventContract.methods.tickets(result).call({from: tBAddress}, function(error2, result2){
                 alert(result2);
               }); 
              */
              });		
              
          });
          
        });
      }
      
      handleMessaggio(key){
        this.setState({
          chiave: key.target.value
        })
      }
        

      componentDidMount(){
        var auth = 'Bearer '.concat(sessionStorage.getItem('serverToken'))
        let header= {
          headers: {'Authorization': auth}
        };
        fetch("http://localhost:8081/event/"+this.props.match.params.id+"/purchaseRequests", header)
        .then(res => res.json())
        .then(
          (items) => {
            for (var i = 0; i < items.length; i++){
              if (items[i].status === 'FAILED'){
                this.setState({
                  failed_purchase: [...this.state.failed_purchase, items[i]]
                });
              }
              else{
                  this.setState({
                    items: [...this.state.items, items[i]]
                  })
                }
            }
            this.setState({
              isLoaded: true
              });
          },
          // Note: it's important to handle errors here
          // instead of a catch() block so that we don't swallow
          // exceptions from actual bugs in components.
          (error) => {
          this.setState({
              isLoaded: true,
              error
          });
          }
      )
      }


    goBack(){
        window.location.href="/ticketBuyer/events"
    }

    sbagliaPagamento = (idRichiesta) => {
      var auth = 'Bearer '.concat(sessionStorage.getItem("serverToken"))

      fetch('http://localhost:8081/purchaseRequest/'+idRichiesta+'/buy/false', {
        method: 'PUT',
        headers: {
          'Authorization': auth,
          'Accept': 'application/json , */*',
          'Content-Type': 'application/json',
        },
      })
      alert('AAAAAAA! Non sono riuscito a pagare :(')
      window.location.reload();
    }

    pagaBiglietto = (idRichiesta) => {
      var auth = 'Bearer '.concat(sessionStorage.getItem("serverToken"))

      fetch('http://localhost:8081/purchaseRequest/'+idRichiesta+'/buy/true', {
        method: 'PUT',
        headers: {
          'Authorization': auth,
          'Accept': 'application/json , */*',
          'Content-Type': 'application/json',
        },
      })
      alert('Pagamento effettuato con successo!')
      this.goBack()
    }


    render(){
        return(
        <div classNameName="event-list">
        <h5 style={{marginBottom: '50px'}}>Ecco le richieste di acquisto: </h5>

        {/* RICHIESTE RIFIUTATE DAL RESELLER */}
        {this.state.failed_purchase.map(request =>(
      <div className="card text-center">
      <div className="card-header">
        <ul className="nav nav-tabs card-header-tabs">
          <li className="nav-item">
            La tua richiesta: {request.description}
            <Card>
          <CardBody>
            <CardTitle tag="h5">
              <img src={ticketPic} alt = "" style={{width:'25%'}}/>
              Il reseller ha rifiutato la tua richiesta
            </CardTitle>
            <CardText style={{marginTop: '20px'}}>
               Risultato: {request.status} <br />
               Pagamenti falliti: {request.failedPayment} <br />
               Risposta dal reseller: {request.responseText}
            </CardText>

          </CardBody>
        </Card>
          </li>
        </ul>
      </div>
    </div>
    ))}


      {/*TUTTE LE ALTRE RICHIESTE*/}
      {this.state.items.map(request =>(
      <div className="card text-center">
      <div className="card-header">
        <ul className="nav nav-tabs card-header-tabs">
          
          <li className="nav-item">
            La tua richiesta: {request.description}
            <Card>
          <CardBody>
            <CardTitle tag="h5">
              <img src={ticketPic} alt = "" style={{width:'25%'}}/>
              Biglietto codice {request.ticket.id}
            </CardTitle>
            <CardText style={{marginTop: '20px'}}>
               Stato: {request.ticket.state} <br />
               Prezzo: {request.ticket.price} <br />
               Tipo: {request.ticket.type} <br />
               Risultato: {request.status} <br />
               Pagamenti falliti: {request.failedPayment} <br />
               Risposta dal reseller: {request.responseText}
            </CardText>

            <button className="btn_pagamento" disabled={request.status==='SUCCEED'} onClick={() => this.sbagliaPagamento(request.id)}>
                Pagamento Fallito
            </button>

            <button className="btn_pagamento" disabled={request.status==='SUCCEED'} onClick={() => this.pagaBiglietto(request.id)}>
                Paga Biglietto
            </button>

            <form style={{border: '2px',marginBottom: '10px'}} >
                <label>
                  Chiave Eth: 
                  <input style={{marginLeft: '5px', width:'300px'}} type="password" onChange={this.handleMessaggio.bind(this)}/>
              </label>
            </form>

            <button className="btn_pagamento" disabled={request.ticket.state!=='SELLED'} onClick={() => this.onClickTestWeb3Javascript(request.event.id,request.id,this.state.chiave)}>
                Verifica proprietario
            </button>

          </CardBody>
        </Card>
          </li>

        </ul>

      </div>
      
    </div>
    ))}

  

          <Button style={{color: 'black'}} onClick={this.goBack.bind(this)}>Torna indietro</Button>
    </div>
        )
    }
}

export default BuyerPurchaseRequests;