import React from "react";
import {Button} from "reactstrap";


class EventResellerList extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
          error: null,
          isLoaded: false,
          items: [],
          privateKey: ''
        };
      }

      handleKey(chiave){
        this.setState({
          privateKey: chiave.target.value
        })
      }

      goBack(){
        window.location.href = "/eventManager/events"
      }

    acceptReseller = (resellerId) => {
      var auth = 'Bearer '.concat(sessionStorage.getItem('serverToken'))
      fetch('http://localhost:8081/eventReseller/'+resellerId+'/accept', {
        method: 'PUT',
        headers: {
          'Accept': 'application/json , */*',
          'Content-Type': 'application/json',
          'Authorization': auth
        },
        body: JSON.stringify({
          pk: this.state.privateKey
        })
      })
      alert('Il reseller è stato accettato!')
      window.location.reload()
    }


      componentDidMount(){
        var auth = 'Bearer '.concat(sessionStorage.getItem('serverToken'))
        let header= {
        headers: {'Authorization': auth}
        };
        fetch("http://localhost:8081/event/"+this.props.match.params.id +"/findNewEventReseller", header)
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
         Ecco gli Event Reseller:
          <div style={{marginTop : "50px"}} classNameName="event-list">
            {items.map(eventReseller =>(
            <div className="card text-center">
              <div className="card-header">
                <ul className="nav nav-tabs card-header-tabs">
                  <li className="nav-item">
                    <div className="card-body">
                      <h5 className="card-title">{eventReseller.ticketReseller.publicName}</h5>

                        <form style={{border: '2px'}} >
                          <label>
                            Chiave private Ethereum: 
                            <input style={{marginLeft: '5px'}} type="password" onChange={this.handleKey.bind(this)}/>
                          </label>
                          <Button className="button-color" color="secondary" style={{marginTop:'30px'}}
                             onClick={() => this.acceptReseller(eventReseller.id)}>Accetta questo reseller
                          </Button>
                        </form>

                       
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

export default EventResellerList;