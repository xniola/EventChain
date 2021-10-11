import React from "react";
import GoogleLogin from 'react-google-login';

import blockchaingif from '../images/block3.gif';
import tokengif from '../images/token.gif';
import validita from '../images/validita.gif';



class Welcome extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
          authCode: '',
          serverToken: ''
        };
    }

    responseGoogle = response => {
      this.setState({
        authCode: response.code
      })
      fetch('http://localhost:8081/auth/google', {
        method: 'POST',
        headers: {
          'Accept': 'application/json , */*',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          code: response.code
        })
      }).then(function(response) {
         return response.json();
      })
      .then( myJson => {
        this.setState({
          serverToken: myJson.token
        });
        sessionStorage.setItem('serverToken',myJson.token)
      })
    }
    
    failureGoogle(response){
      alert('Fail: '+response)
    }

    componentDidMount(){
      sessionStorage.clear()
    }
    
    render(){
      if (this.state.serverToken !== ''){
        window.location.href= '/user'
      }
      return(
        <div>
          <h1 className='welcome'>Benvenuto in EventChain!</h1>
          <p className='par'>L'applicazione che ti consente di prenotare facilmente biglietti per concerti ed eventi</p>
          <h5 className='welcome-body'>Perchè usare EventChain:</h5>

          <ul>
            <li>
              <h4 style={{marginTop:'20px',marginBottom: '20px',color:'#5a94d3'}} >I tuoi biglietti saranno conservati in Blockchain!</h4>
              <img style={{marginTop:'60px',marginBottom:'60px'}} src={blockchaingif} alt="loading..."  class="gif"/>
            </li>

            <li>
            <h4 className='welcome-li'>La catena convaliderà l'autenticità e la proprietà di ogni biglietto!</h4>
              <img src={validita} alt="loading..."  class="gif3"/>
            </li>

            <li>
              <h4 className='welcome-li'>Lo standard ERC-721 previene la vendita di biglietti falsi!</h4>
              <img style={{marginTop: '40px',marginLeft:'170px'}} src={tokengif} alt="loading..."  class="gif2"/>
            </li>

          </ul>

        <div style={{margin: 'auto', width: '20%',padding: '20px'}}>
        <h5 style={{marginTop: '50px',color: '#5a94d3',marginBottom: '10px'}}>Accedi ora!</h5>
          <GoogleLogin
            clientId="583922006217-k975eb3ph9vbjti3n2j9ofml0v1kg8ll.apps.googleusercontent.com"
            responseType="code"
            buttonText="Login"
            accessType="offline"
            onSuccess={this.responseGoogle}
            onFailure={this.failureGoogle}
            redirectUri='postmessage'
            cookiePolicy={'single_host_origin'}
          />
        </div>
        </div>
      )}
}

export default Welcome;