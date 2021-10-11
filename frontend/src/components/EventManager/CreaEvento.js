import React from 'react'

import { Button } from "reactstrap";

class CreaEvento extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
          pubblicazione: false,
          titolo: '',
          descrizione: '',
          orario: '',
          fine: '',
          luogo: '',
          chiaveEthereum: ''
        };
      }

      handleTitolo(titolo) {
        this.setState({
          titolo: titolo.target.value
        });  
      }

      handleDescrizione(descrizione) {
        this.setState({
          descrizione: descrizione.target.value
        });  
      }

      handleInizio(inizio){
        this.setState({
          orario: inizio.target.value
        })
      }

      handleFine(fine){
        this.setState({
          fine: fine.target.value
        })
      }

      handleLuogo(luogo){
       this.setState({
         luogo: luogo.target.value
       })
      }

      handlePubblicazione = () => {
        var auth = 'Bearer '.concat(sessionStorage.getItem("serverToken"))

        fetch('http://localhost:8081/event/create', {
          method: 'POST',
          headers: {
            'Authorization': auth,
            'Accept': 'application/json , */*',
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            title: this.state.titolo,
            description: this.state.descrizione,
            start: this.state.orario,
            end: this.state.fine,
            location: this.state.luogo,
            pk: this.state.chiaveEthereum
          })
        })
        alert('Il tuo evento è stato pubblicato!')
        window.location.href= '/user'
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
                <form className='formevento' >
                    <label>
                        Titolo Evento: 
                        <input className='inputevento' type="text" onChange={this.handleTitolo.bind(this)}/>
                    </label>
                </form>

                <form className='formevento' >
                    <label>
                        Descrizione Evento: 
                        <input className='inputevento' type="text" onChange={this.handleDescrizione.bind(this)}/>
                    </label>
                </form>

                <form className='formevento' >
                    <label>
                        Orario di inizio: 
                        <input className='inputevento' type="date" onChange={this.handleInizio.bind(this)}/>
                    </label>
                </form>  

                <form className='formevento' >
                    <label>
                        Orario di fine: 
                        <input className='inputevento' type="date" onChange={this.handleFine.bind(this)}/>
                    </label>
                </form>  

                <form className='formevento' >
                    <label>
                        Luogo: 
                        <input className="inputevento" type="text" onChange={this.handleLuogo.bind(this)}/>
                    </label>
                </form>    

                <form className='formevento' >
                    <label>
                        Chiave Ethereum: 
                        <input className='inputevento' type="password" onChange={this.handleChiave.bind(this)}/>
                    </label>
                </form>       

                <Button className="bottone-indietro" 
                  onClick={console.log("yay")}>
                  Indietro
                </Button> 

                <Button className="bottone-creaevento" 
                  onClick={this.handlePubblicazione.bind(this)}  >
                  Invia
                </Button> 
            </div>
        )
    }
}

export default CreaEvento;