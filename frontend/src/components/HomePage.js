import React from 'react'
import EventManager from './EventManager/EventManager.js';
import TicketBuyer from './TicketBuyer/TicketBuyer.js';
import TicketReseller from './TicketReseller/TicketReseller.js';


class HomePage extends React.Component{
    constructor(props) {
      super(props);
      this.state = {
        error: null,
        isLoaded: false,

        scopriEventi: '',
        creaEvento: '',
        indirizzoEthereum: '', 
        fullName: '',
        cognome: '',
        photoUrl: '',
        nome: '',
        email:'',

        // permessi
        ticketBuyer: false,
        eventManager:false,
        ticketReseller:false
      };
    }



    componentDidMount(){
      var auth = 'Bearer '.concat(sessionStorage.getItem('serverToken'))
      let header= {
        headers: {'Authorization': auth}
      };

      fetch("http://localhost:8081/user", header)
      .then(res => res.json())
      .then((items) => {
        sessionStorage.setItem('indirizzo_ethereum', items.ethereumAddress)
        sessionStorage.setItem('nome_utente', items.givenName)
        sessionStorage.setItem('cognome_utente', items.familyName)
        sessionStorage.setItem('foto_utente', items.picture)
        sessionStorage.setItem('fullname_utente', items.name)
        sessionStorage.setItem('email_utente', items.username)
        sessionStorage.setItem('ticketBuyer', items.ticketBuyer)
        sessionStorage.setItem('eventManager', items.eventManager)
        sessionStorage.setItem('ticketReseller', items.ticketReseller)

        this.setState({
            indirizzoEthereum: items.ethereumAddress,
            nome: items.givenName,
            cognome: items.familyName,
            photoUrl: items.picture,
            fullName: items.name,
            email: items.username,
            
            //permessi
            ticketBuyer: items.ticketBuyer,
            eventManager: items.eventManager,
            ticketReseller: items.ticketReseller,
            isLoaded: true
        });
        },
        (error) => {
        this.setState({
            isLoaded: true,
            error
        });
        }
    )
    }
    
    
  render(){
    const { error, isLoaded } = this.state;
    if (error) return <div>Error: {error.message}</div>;
    else if (!isLoaded) return <div>Loading...</div>;

    if(this.state.eventManager)
    return ( <EventManager /> );

    else if(this.state.ticketBuyer)
      return( <TicketBuyer />) ;

    else if (this.state.ticketReseller)
      return (<TicketReseller />)
    }
  }

export default HomePage;