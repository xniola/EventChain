import React from 'react'
import { Button } from 'reactstrap';


class EventManagerEventi extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          selected_id_event: 0,
          openfaq: false,
          items: [],
          key: ''
        };
      }

      closeEvent(id){
        window.location.href = "/event/"+id+"/close"
      }

      deleteEvent(id){
        window.location.href = "/event/"+id+"/delete"
      }

      componentDidMount(){
        var auth = 'Bearer '.concat(sessionStorage.getItem('serverToken'))
        let header= {
          headers: {'Authorization': auth}
        };
        fetch("http://localhost:8081/events", header)
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
        window.location.href="/user"
    }

    findReseller(id){
        window.location.href = "/event/"+id+"/findNewEventReseller"
    }

    trovaBiglietti(id){
        window.location.href = "/event/"+id+"/tickets"
    }

    creaTicket = (id) => {
        var auth = 'Bearer '.concat(sessionStorage.getItem("serverToken"))

        fetch('http://localhost:8081/event/'+id+'/createTickets/1', {
          method: 'POST',
          headers: {
            'Authorization': auth,
            'Accept': 'application/json , */*',
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            price: 1000,
            type: 'TRIBUNA'
          })
        })
        alert("Il nuovo ticket è stato creato!")
      }



    render(){
        return(
        <div classNameName="event-list">
      <h5 style={{marginBottom:'35px'}}>Ecco la lista degli eventi:</h5>
      {this.state.items.map(event =>(
      <div className="card text-center">
      <div className="card-header">
        <ul className="nav nav-tabs card-header-tabs">
          <li className="nav-item">
          <Button className="button-color" color="primary" onClick={() => this.trovaBiglietti(event.id)} >Trova Biglietti</Button> 
          </li>
          <li className="nav-item">
          <Button className="button-color" color="secondary"
           onClick={() => this.creaTicket(event.id)} >Crea un Ticket</Button>

          </li>
          <li className="nav-item">
          <Button className="button-color" color="primary" 
          onClick={() => this.closeEvent(event.id)} >Chiudi Evento</Button>
          </li>

          <li>
          <Button style={{backgroundColor:'#CD5C5C', color:'black'}}
           onClick={() => this.deleteEvent(event.id)} >Elimina Evento</Button>
          </li>
        </ul>

      </div>
      <div className="card-body">
        <h5 className="card-title">{event.title}</h5>
        <h6 className="card-title">Luogo: {event.location}</h6>
        <h6 className="card-title">Inizio: {event.start.substring(0,19).replace('T', ' ')}</h6>
        <h6 className="card-title">Fine: {event.end.substring(0,19).replace('T', ' ')}</h6>
        <p className="card-text">{event.description}</p>
        <Button className="button-color" color="secondary" onClick={() => this.findReseller(event.id)} >Visualizza richieste di reselling</Button>
      </div>
    </div>
    ))}

          <Button style={{color: 'black'}} onClick={this.goBack.bind(this)}>Torna indietro</Button>
    </div>
        )
    }
}

export default EventManagerEventi;