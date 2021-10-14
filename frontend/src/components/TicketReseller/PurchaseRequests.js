import React from 'react'
import { Card, CardText, CardBody,
  CardTitle, CardSubtitle, Button } from 'reactstrap';


class PurchaseRequests extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          isLoaded: false,
          items: [],
          idBiglietto: 0
        };
      }

      cercaBiglietto = (idEvento) => {
        var auth = 'Bearer '.concat(sessionStorage.getItem('serverToken'))
        let header= {
          headers: {'Authorization': auth}
        };
        fetch("http://localhost:8081/event/"+idEvento+"/tickets", header)
        .then(res => res.json())
        .then(
          (items) => {
            if(items[0]){
              this.setState({
                  isLoaded: true,
                  idBiglietto: items[0].id
            })}
            else{
              this.setState({
                isLoaded:true
              })
            }
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

      validaBiglietto = (idRichiesta) => {
        var auth = 'Bearer '.concat(sessionStorage.getItem("serverToken"))

        fetch('http://localhost:8081/purchaseRequest/'+idRichiesta+'/accept/'+this.state.idBiglietto, {
          method: 'PUT',
          headers: {
            'Authorization': auth,
            'Accept': 'application/json , */*',
            'Content-Type': 'application/json',
          },
        })
        alert('Richiesta accettata!')
        this.goBack()
      }

      goBack(){
        window.location.href = "/ticketReseller/events"
      }
    


      componentDidMount(){
        var auth = 'Bearer '.concat(sessionStorage.getItem('serverToken'))
        let header= {
          headers: {'Authorization': auth}
        };
        fetch("http://localhost:8081/event/"+this.props.match.params.id+"/resellerPurchaseRequests", header)
        .then(res => res.json())
        .then(
          (items) => {
          this.setState({
              isLoaded: true,
              items
          });
          this.cercaBiglietto(this.props.match.params.id);
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
        window.location.href="/ticketReseller/events"
    }

    reload(){
      window.location.reload();
    }

    completaAcuisto = (idRichiesta) => {
      var auth = 'Bearer '.concat(sessionStorage.getItem("serverToken"))
      fetch('http://localhost:8081/purchaseRequest/'+idRichiesta+'/complete', {
        method: 'PUT',
        headers: {
          'Authorization': auth,
          'Accept': 'application/json , */*',
          'Content-Type': 'application/json',
        },
      })
      alert('Acquisto completato!')
      this.reload()
    }


    render(){
        return(
        <div classNameName="event-list">
        <h5 style={{marginBottom: '50px'}}>Ecco le richieste di acquisto: </h5>
      {this.state.items.map(request =>(
      <div className="card text-center">
      <div className="card-header">

      <Card>
        <CardBody>
          <CardTitle tag="h5">
          Messaggio: {request.description}
          </CardTitle>
          <CardSubtitle tag="h6" className="mb-2 text-muted">Sigillo fiscale: 0x{Buffer.from(request.ticket.taxSeal, 'utf8').toString('hex').substring(0,32)} </CardSubtitle>
          <CardText style={{marginTop: '20px'}}>
          Evento: {request.event.title}
          </CardText>
          <CardText style={{marginTop: '20px'}}>
          Data: {request.timestamp.substring(0,19).replace('T', ' ')}
          </CardText>
          <CardText>
          Pagato: {request.paymentTime.substring(0,19).replace('T', ' ')} ({request.ticket.state})
          </CardText>
          <CardText>
          Status: {request.status} 
          </CardText>
          
          <button className="btn_pagamento" disabled={request.ticket.state=='PAYED'} style={{color: 'black', marginLeft:'300px'}}
          onClick={() => this.validaBiglietto(request.id)}>Accetta</button>

          <button className="btn_pagamento" disabled={!request.ticket.state=='PAYED'} style={{color: 'black', marginLeft:'300px'}}
            onClick={() => this.completaAcuisto(request.id)}>Completa acquisto</button>
        </CardBody>    
      </Card>
      </div>
      
    </div>
    ))}

          <Button style={{color: 'black'}} onClick={this.goBack.bind(this)}>Torna indietro</Button>
    </div>
        )
    }
}

export default PurchaseRequests;