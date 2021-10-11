import React from 'react'
import { Button } from 'reactstrap';


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



    render(){
        return(
        <div classNameName="event-list">
        <h5 style={{marginBottom: '50px'}}>Ecco le richieste di acquisto: </h5>
      {this.state.items.map(request =>(
      <div className="card text-center">
      <div className="card-header">
        <ul className="nav nav-tabs card-header-tabs">
          
          <li className="nav-item">
            Messaggio: {request.description} <br/> 
            Data: {request.timestamp.substring(0,19).replace('T', ' ')} <br/>
            Pagato: {request.paymentTime.substring(0,19).replace('T', ' ')} <br/>
            Sigillo fiscale: 0x{Buffer.from(request.ticket.taxSeal, 'utf8').toString('hex').substring(0,8)} ...
          </li>
          
          <button className="btn_pagamento" disabled={request.ticket.state=='PAYED'} style={{color: 'black', marginLeft:'300px'}}
            onClick={() => this.validaBiglietto(request.id)}>Accetta</button>
        </ul>

      </div>
      
    </div>
    ))}

          <Button style={{color: 'black'}} onClick={this.goBack.bind(this)}>Torna indietro</Button>
    </div>
        )
    }
}

export default PurchaseRequests;