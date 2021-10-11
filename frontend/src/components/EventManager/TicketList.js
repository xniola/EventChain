import React from 'react'

import {
    Card, CardText, CardBody,
    CardTitle, Button
  } from 'reactstrap';

import ticketPic from '../../images/ticket2.jpg'

class TicketList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          error: null,
          isLoaded: false,
          items: []
        };
      }


    goBack(){
        window.location.href="/eventManager/events"
    }

    deleteTicket = (id) => {
        var auth = 'Bearer '.concat(sessionStorage.getItem("serverToken"))

        fetch('http://localhost:8081/ticket/'+id+'/delete', {
          method: 'DELETE',
          headers: {
            'Authorization': auth,
            'Accept': 'application/json , */*',
            'Content-Type': 'application/json',
          },
        })
        alert("Il tuo biglietto è stato eliminato")
        window.location.reload()
   }

    componentDidMount(){
        var auth = 'Bearer '.concat(sessionStorage.getItem('serverToken'))
        let header= {
          headers: {'Authorization': auth}
        };
        fetch("http://localhost:8081/event/"+this.props.match.params.id+"/tickets", header)
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
        
        return(
        <div classNameName="event-list">
          <h5>Ecco i biglietti dell'evento:</h5>
      {items.map(ticket =>(
        <div>
        <Card>
          <CardBody>
            <CardTitle tag="h5">
              <img src={ticketPic} alt = "" style={{width:'25%'}}/>
              Biglietto codice {ticket.id}
            </CardTitle>
            <CardText style={{marginTop: '20px'}}>
               Stato: {ticket.state} <br />
               Prezzo: {ticket.price} <br />
               Tipo: {ticket.type}
            </CardText>

            <Button className="button-color" color="primary" 
              onClick={() => this.deleteTicket(ticket.id)} >
                Elimina Biglietto
          </Button>

          </CardBody>
        </Card>
        </div>
    ))}

          <Button style={{color: 'black'}} onClick={this.goBack.bind(this)}>Torna indietro</Button>
    </div>
        )
    }
}

export default TicketList;