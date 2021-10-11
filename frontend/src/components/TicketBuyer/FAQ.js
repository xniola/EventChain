import { Button } from "reactstrap";
import React from "react";


class FAQ extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
          error: null,
          isLoaded: false,
          items: [],
          commento: '' /* Per la domanda delle FAQ */
        };
      }

      goBack(){
        window.location.href = "/ticketBuyer/events"
      }

      handleChange(event) {
        this.setState({
          commento: event.target.value
        });  
      }

    
    pubblica = () => {
      var auth = 'Bearer '.concat(sessionStorage.getItem('serverToken'))
      fetch('http://localhost:8081/event/'+this.props.match.params.id+'/createComment', {
        method: 'POST',
        headers: {
          'Accept': 'application/json , */*',
          'Content-Type': 'application/json',
          'Authorization': auth
        },
        body: JSON.stringify({
          body: this.state.commento,
        })
      })
      alert('Il tuo commento è stato pubblicato!')
      window.location.reload()
    }


    componentDidMount(){
    var auth = 'Bearer '.concat(sessionStorage.getItem('serverToken'))
    let header= {
    headers: {'Authorization': auth}
    };
    fetch("http://localhost:8081/event/"+this.props.match.params.id +"/comments", header)
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
    if (error) {
      return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
      return <div>Loading...</div>;
    }
    else {
      return (
        <div>
          <h5>Benvenuto nella sezione FAQ dell'evento!</h5>
          <h4>Fai qui le tue domande</h4>

          <div style={{marginTop : "50px"}} classNameName="event-list">
            {items.map(commento =>(
            <div className="card text-center">
              <div className="card-header">
                <ul className="nav nav-tabs card-header-tabs">
                  <li className="nav-item">
                    <div className="card-body">
                      <h5 className="card-title">{commento.sender.publicName}: (il {commento.timestamp.substring(0,19).replace('T', ' ')})</h5>
                      <p className="card-text">{commento.body}</p>
                    </div>
                  </li>
                </ul>
              </div>
           </div>
            ))}
         </div>

         <center>
         <form style={{border: '2px'}} >
          <label>
            Fai una domanda qui: 
             <input style={{marginLeft: '5px'}} type="text" onChange={this.handleChange.bind(this)}/>
          </label>
          <Button style={{marginLeft: '15px',backgroundColor: '#007bff', color: '#fff'}} onClick={this.pubblica.bind(this)} >Invia</Button> 
      </form>
      </center>
      <Button style={{color: 'black', marginTop:'50px'}} onClick={this.goBack.bind(this)}>Torna indietro</Button>
      </div>
      )}
  }
}
 
export default FAQ;