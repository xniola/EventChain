import React from 'react'
import  {Button} from 'reactstrap';


class TicketResellerEventi extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          selected_id_event: 0,
          openfaq: false,
          items: [],
          chiusi: []
        };
      }

      purchaseRequests(id){
        window.location.href = "/event/"+id+"/resellerPurchaseRequests"
      }

      goBack(){
        window.location.href = '/user'
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
            for (var i = 0; i < items.length; i++){
              if (items[i].opened == false){
                this.setState({
                  chiusi: [...this.state.chiusi, items[i]]
                });
              }
              else{
                  this.setState({
                    items: [...this.state.items, items[i]]
                  })
                }
            }
          this.setState({
              isLoaded: true,
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
          <Button className="button-color" color="secondary" onClick={() => this.purchaseRequests(event.id)} >Richieste di acquisto</Button>
          </li>
        </ul>

      </div>
      <div className="card-body">
        <h5 className="card-title">{event.title}</h5>
        <p className="card-text">{event.description}</p>
        <a href={"/event/"+event.id+"/createEventReseller"} className="btn btn-primary button-color">Fai richiesta di reselling</a>
      </div>
    </div>
    ))}


    {this.state.chiusi.map(event =>(
      <div className="card text-center">
      <div className="card-header">
        <ul className="nav nav-tabs card-header-tabs">
        </ul>
      </div>
      <div className="card-body">
        <h5 className="card-title">{event.title}</h5>
        <p className="card-text">Questo evento è stato chiuso</p>
       
      </div>
    </div>
    ))}

    <Button style={{color: 'black'}} onClick={ this.goBack.bind(this) }>Torna indietro</Button>
    </div>
        )
    }
}

export default TicketResellerEventi;