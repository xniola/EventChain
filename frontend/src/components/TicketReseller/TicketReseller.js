import React from 'react'
import {
    Card, CardText, CardBody,
    CardTitle, CardSubtitle, Button
  } from 'reactstrap';
  
  
  
const emoji = require("emoji-dictionary");


class TicketReseller extends React.Component {
    seeEvents(){
        window.location.href = '/ticketReseller/events'
    }

    render(){
        return(
            <div>
            <Card>
              <CardBody>
                <CardTitle tag="h5">
                  <img className='google-photo' src={sessionStorage.getItem("foto_utente")} alt = "" />
                  Ciao {sessionStorage.getItem("nome_utente")}! Hai la qualifica di Ticket Reseller.
                </CardTitle>
                <CardSubtitle tag="h6" className="mb-2 text-muted">E' un piacere vederti su EventChain!</CardSubtitle>
                <CardText style={{marginTop: '20px'}}>
                   {emoji.getUnicode("bust_in_silhouette")}: {sessionStorage.getItem("fullname_utente")}{"\n"}
                </CardText>
                <CardText>
                   {emoji.getUnicode('e-mail')}: {sessionStorage.getItem("email_utente")}
                </CardText>
                <CardText>
                  I tuoi permessi: <br />
                  {emoji.getUnicode("x")} :Ticket Buyer <br />
                  {emoji.getUnicode("x")} :Event Manager<br />
                  {emoji.getUnicode("white_check_mark")} :Ticker Reseller<br />
                </CardText>
    
              <Button className="button-color" color="primary" 
                  onClick={ this.seeEvents } >
                    Visualizza gli eventi
              </Button> 
    
              <Button className="button-color" color="primary"
                  onClick={ this.createEvent } >
                     Crea un evento
              </Button> 
    
              <a href="/" className="btn btn-primary button-color">Logout</a>
              </CardBody>
    
              
            </Card>
            </div>
        )
    }
}

export default TicketReseller;