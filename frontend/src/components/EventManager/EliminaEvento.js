import React from 'react'

import { Button } from "reactstrap";

class EliminaEvento extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
          key: ''
        };
      }

      goBack(){
        window.location.href = "/eventManager/events"
      }
       
    eliminaEvento = (id) => {
        var auth = 'Bearer '.concat(sessionStorage.getItem("serverToken"))
  
        fetch('http://localhost:8081/event/'+id+'/delete' ,{
          method: 'DELETE',
          headers: {
            'Authorization': auth,
            'Accept': 'application/json , */*',
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            pk: this.state.key
          })
        })
        alert("L'evento è stato eliminato!")
        this.goBack()
      }
      

      handleChiave(chiave){
       this.setState({
         key: chiave.target.value
       })
      }



    render(){
        return (
            <div>
                <h5>Se sei sicuro di voler cancellare l'evento inserisci la tua chiave</h5>
                    <h4>Attenzione: l'azione è irreversibile</h4>

                <form className='formevento' style={{marginTop:'50px'}}>
                    <label>
                        Chiave privata Ethereum:
                        <input type="password" onChange={this.handleChiave.bind(this)}/>
                    </label>
                    <Button style={{marginLeft: '15px',backgroundColor: '#007bff', color: '#fff'}} 
                        onClick={() => this.eliminaEvento(this.props.match.params.id)} >Elimina
                    </Button> 
                </form>
                <Button style={{color: 'black', marginTop:'50px'}} onClick={this.goBack.bind(this)}>Torna indietro</Button>
            </div>
        )
    }
}

export default EliminaEvento;