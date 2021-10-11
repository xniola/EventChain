import React from 'react'
import { Button ,Card, CardText, CardBody, CardTitle } from 'reactstrap';


import ticketPic from '../../images/ticket2.jpg'


class BuyerPurchaseRequests extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          isLoaded: false,
          items: []
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
          this.setState({
              isLoaded: true,
              items
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
               Risultato: {request.status}
            </CardText>

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