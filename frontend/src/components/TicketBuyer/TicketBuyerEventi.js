import React from 'react'
import { Button } from 'reactstrap';


class TicketBuyerEventi extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          selected_id_event: 0,
          openfaq: false,
          items: []
        };
      }

      goBack(){
        window.location.href="/user"
      }

      purchaseRequests(id){
        window.location.href="/event/"+id+"/purchaseRequests"
      }

      handleFAQ(id){
        window.location.href = "/event/"+id+"/comments"
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

    render(){
        return(
            <div classNameName="event-list">
      {this.state.items.map(event =>(
      <div className="card text-center">
      <div className="card-header">
        <ul className="nav nav-tabs card-header-tabs">
          <li className="nav-item">
          <Button className="button-color" color="primary" 
            onClick={() => this.purchaseRequests(event.id)} >Richieste d'acquisto</Button> 
          </li>

          <li className="nav-item">
          <Button className="button-color" color="secondary" onClick={() => this.handleFAQ(event.id)} >FAQ</Button>
          </li>

        </ul>

      </div>
      <div className="card-body">
        <h5 className="card-title">{event.title}</h5>
        <p className="card-text">{event.description}</p>
        <a href={"/event/"+event.id+"/resellers"} className="btn btn-primary button-color">Cerca dei resellers</a>
      </div>
    </div>
    ))}

    <Button style={{color: 'black'}} onClick={this.goBack.bind(this)}>Torna indietro</Button>
    </div>
        )
    }
}

export default TicketBuyerEventi;