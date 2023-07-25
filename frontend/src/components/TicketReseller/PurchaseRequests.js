import React from 'react'
import { Card, CardText, CardBody,
  CardTitle, CardSubtitle, Button } from 'reactstrap';


class PurchaseRequests extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          isLoaded: false,
          items: [],
          tickets: [],
          chiaveEth: '',
          messaggio: '',
          
          // per distinguere le requests
          risposti: [],
          non_risposti: [],
          non_pagati: []
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
              this.setState({
                  isLoaded: true,
                  tickets: items
            })
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

        fetch('http://localhost:8081/purchaseRequest/'+idRichiesta+'/accept/'+this.state.tickets[0].id, {
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

      handleChange(chiave){
        this.setState({
          chiaveEth: chiave.target.value 
        })
      }
    
      handleMessaggio(mex){
        this.setState({
          messaggio: mex.target.value
        })
      }

      componentDidMount(){
        this.cercaBiglietto(this.props.match.params.id);
        var auth = 'Bearer '.concat(sessionStorage.getItem('serverToken'))
        let header= {
          headers: {'Authorization': auth}
        };
        fetch("http://localhost:8081/event/"+this.props.match.params.id+"/resellerPurchaseRequests", header)
        .then(res => res.json())
        .then(
          (items) => {
            for (var i = 0; i < items.length; i++){
              if (items[i].ticket == null){
                this.setState({
                  non_risposti: [...this.state.non_risposti, items[i]]
                });
              }
              else{
                if(items[i].ticket.taxSeal == null){
                  this.setState({
                    non_pagati: [...this.state.non_pagati, items[i]]
                  })
                }
                else{
                this.setState({
                  risposti: [...this.state.risposti, items[i]]
                });
                }
              }
            }
          this.setState({
              isLoaded: true,
              //items
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
        window.location.href="/ticketReseller/events"
    }

    reload(){
      window.location.reload();
    }
  
    completaAcquisto = (idRichiesta) => {
      var auth = 'Bearer '.concat(sessionStorage.getItem("serverToken"))
      fetch('http://localhost:8081/purchaseRequest/'+idRichiesta+'/complete', {
        method: 'PUT',
        headers: {
          'Authorization': auth,
          'Accept': 'application/json , */*',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          text: this.state.messaggio,
          pk: this.state.chiaveEth
        })
      })
      alert('Richiesta completata!')
      this.reload()
    }


    render(){
        return(
          
        <div classNameName="event-list">
        <h5 style={{marginBottom: '50px'}}>Ecco le richieste di acquisto: </h5>
      {/*NON RISPOSTI*/}
      {this.state.non_risposti.map(request =>(
        <div className="card text-center">
        <div className="card-header">
        
        <Card>
          <CardBody>
            <CardTitle tag="h5">
            Messaggio: {request.description}
            </CardTitle>
            <CardSubtitle tag="h6" className="mb-2 text-muted">In attesa di risposta o rifiutato</CardSubtitle>
            <CardText style={{marginTop: '20px'}}>
            Evento: {request.event.title}
            </CardText>
            <CardText style={{marginTop: '20px'}}>
            Data: {request.timestamp.substring(0,19).replace('T', ' ')}
            </CardText>
            <CardText>
            Status: {request.status} 
            </CardText>
            
            <button className="btn_pagamento" style={{color: 'black', marginLeft:'300px'}} disabled={request.status!=='INIT'}
            onClick={() => this.validaBiglietto(request.id)}>Accetta</button>
          </CardBody>    
        </Card>
        </div>
        
      </div>
      ))}


      {/*NON PAGATI*/}
      {this.state.non_pagati.map(request =>(
        <div className="card text-center">
        <div className="card-header">
        
        <Card>
          <CardBody>
            <CardTitle tag="h5">
            Messaggio: {request.description}
            </CardTitle>
            <CardSubtitle tag="h6" className="mb-2 text-muted">Pagamento fallito dal buyer (num. fallimenti: {request.failedPayment}) </CardSubtitle>
            <CardText style={{marginTop: '20px'}}>
            Evento: {request.event.title}
            </CardText>
            <CardText style={{marginTop: '20px'}}>
            Data: {request.timestamp.substring(0,19).replace('T', ' ')}
            </CardText>
            <CardText>
            Status: {request.status} 
            </CardText>

            <form style={{border: '2px'}} >
            <label>
              Messaggio:
              <input style={{marginLeft: '5px', marginBottom: '10px'}} type="text" onChange={this.handleMessaggio.bind(this)}/>
            </label>
         </form>
          
          <form style={{border: '2px'}} >
            <label>
              Chiave Eth:
              <input style={{marginLeft: '5px', marginBottom: '10px'}} type="password" onChange={this.handleChange.bind(this)}/>
            </label>
         </form>
            
            <button className="btn_pagamento" style={{color: 'black', marginLeft:'300px'}} disabled={request.status==='SUCCEED'}
            onClick={() => this.completaAcquisto(request.id)}>Rifiuta acquisto</button>
          </CardBody>    
        </Card>
        </div>
        
      </div>
      ))}



      {/*RISPOSTI*/}
      {this.state.risposti.map(request =>(
        
          
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

          <form style={{border: '2px'}} >
            <label>
              Messaggio:
              <input style={{marginLeft: '5px', marginBottom: '10px'}} type="text" onChange={this.handleMessaggio.bind(this)}/>
            </label>
         </form>
          
          <form style={{border: '2px'}} >
            <label>
              Chiave Eth:
              <input style={{marginLeft: '5px', marginBottom: '10px'}} type="password" onChange={this.handleChange.bind(this)}/>
            </label>
         </form>

          <button className="btn_pagamento" disabled={request.ticket.state==='SELLED'} style={{color: 'black', marginLeft:'300px'}}
            onClick={() => this.completaAcquisto(request.id)}>Completa acquisto</button>
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