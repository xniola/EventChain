import React from "react";
import {Button} from "reactstrap";

class EventResellers extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
          error: null,
          isLoaded: false,
          items: [],

          data: '',
          pk: ''
        };
      }

      handleMessaggio(messaggio) {
        this.setState({
          data: messaggio.target.value
        });  
      }

      handlePk(chiave){
        this.setState({
          pk: chiave.target.value
        })
      }

      purchaseRequest = (eventResellerId) => {
        var auth = 'Bearer '.concat(sessionStorage.getItem('serverToken'))
        fetch('http://localhost:8081/eventReseller/'+eventResellerId+'/createPurchaseRequest', {
          method: 'POST',
          headers: {
            'Accept': 'application/json , */*',
            'Content-Type': 'application/json',
            'Authorization': auth
          },
          body: JSON.stringify({
            text: this.state.data,
            pk: this.state.pk
          })
        })
        alert('La richiesta è stata inviata!')
        window.location.reload()
      }

      goBack(){
          window.location.href="/ticketBuyer/events"
      }

      componentDidMount(){
        var auth = 'Bearer '.concat(sessionStorage.getItem('serverToken'))
        let header= {
        headers: {'Authorization': auth}
        };
        fetch("http://localhost:8081/event/"+this.props.match.params.id +"/resellers", header)
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
     

  render(){
    const { error, isLoaded, items } = this.state;
    if (error) return <div>Error: {error.message}</div>;
    else if (!isLoaded) return <div>Loading...</div>;
    
    else {
      return (
        <div>
         <h5 style={{marginBottom: '50px'}}>Ecco i resellers dell'evento: </h5>
          <div style={{marginTop : "50px"}} classNameName="event-list">
            {items.map(eventReseller =>(
            <div className="card text-center">
              <div className="card-header">
                <ul className="nav nav-tabs card-header-tabs">
                  <li className="nav-item">
                    <div className="card-body">
                      <h5 className="card-title">Ticket Reseller: {eventReseller.ticketReseller.publicName}</h5>
                      
                      <form  style={{border: '2px'}} >
                      <label>
                        Messaggio: 
                        <input style={{marginLeft: '5px', width:'300px'}} type="text" onChange={this.handleMessaggio.bind(this)}/>
                      </label>
                      
                      <br />
                      
                      <label>
                        Chiave Eth: 
                        <input style={{marginLeft: '5px', width: '300px', marginTop:'20px'}} type="password" onChange={this.handlePk.bind(this)}/>
                      </label>

                      </form>

                      <Button className="button-color" color="secondary" style={{marginTop:'30px'}}
                         onClick={() => this.purchaseRequest(eventReseller.id)}>Fai una richiesta di acquisto
                      </Button>
                    
                    </div>
                  </li>
                </ul>
              </div>
           </div>
            ))}
         </div>
         <Button style={{color: 'black'}} onClick={this.goBack.bind(this)}>Torna indietro</Button>
      </div>
      )
    }
  }
}

export default EventResellers;