import React from 'react'

import { Button } from "reactstrap";

class ResellerRequest extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
          chiaveEthereum: ''
        };
      }

      goBack(){
          window.location.href = "/ticketReseller/events"
      }
     
      handleRichiesta = () => {
        var auth = 'Bearer '.concat(sessionStorage.getItem("serverToken"))

        fetch('http://localhost:8081/event/'+this.props.match.params.id+'/createEventReseller', {
          method: 'POST',
          headers: {
            'Authorization': auth,
            'Accept': 'application/json , */*',
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            pk: this.state.chiaveEthereum
          })
        })
        alert('Richiesta completata!')
        window.location.href= '/ticketReseller/events'
      }

      handleChiave(chiave){
        this.setState({
          chiaveEthereum: chiave.target.value
        })
        sessionStorage.setItem("pk",this.state.chiaveEthereum)
      }

    render(){
        return (
            <div>
                <h5 style={{marginBottom:'50px'}}>Per effettuare la richiesta inserisci la tua chiave privata Ethereum: </h5> 

                <form className='formevento' >
                    <label>
                        Chiave Ethereum: 
                        <input className='inputevento' type="password" onChange={this.handleChiave.bind(this)}/>
                    </label>
                </form>       

                <Button className="bottone-indietro" 
                  onClick={this.goBack.bind(this)}>
                  Indietro
                </Button> 

                <Button className="bottone-creaevento" 
                  onClick={this.handleRichiesta.bind(this)}  >
                  Invia
                </Button> 
            </div>
        )
    }
}

export default ResellerRequest;