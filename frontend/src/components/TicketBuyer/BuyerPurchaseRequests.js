import React from 'react'
import { Button ,Card, CardText, CardBody, CardTitle } from 'reactstrap';


import ticketPic from '../../images/ticket2.jpg'


class BuyerPurchaseRequests extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          isLoaded: false,

          items: [],
          failed_purchase: []
        };
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
              if (items[i].status == 'FAILED'){
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
      this.goBack()
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

            <button className="btn_pagamento" disabled={request.ticket.state=='PAYED'} onClick={() => this.sbagliaPagamento(request.id)}>
                Pagamento Fallito
            </button>

            <button className="btn_pagamento" disabled={request.ticket.state=='PAYED'} onClick={() => this.pagaBiglietto(request.id)}>
                Paga Biglietto
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