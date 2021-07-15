import React from 'react'
import {Dimensions , View, Text, Image, StyleSheet ,ImageBackground} from 'react-native'
import AppIntroSlider from 'react-native-app-intro-slider';



export default class Welcome extends React.Component {
  
goToLogin = () => {
  this.props.navigation.replace("Login");
};


render() {
     return ( 
       <AppIntroSlider slides={slides} onDone={this.goToLogin} 
        showSkipButton={true} 
        onSkip={this.goToLogin} /> 
      ); 
 } 
}

const { width: WIDTH } = Dimensions.get('window')

const slides = [
  {
    key: 'k1',
    title: 'CREA EVENTI',
    text: 'Organizzare un evento non è mai stato così semplice!',
    backgroundColor: '#F7BB64',
    //image: require('../images/JM2.jpg'),
  },
  {
    key: 'k2',
    title: 'PARTECIPA',
    text: 'Prendi parte al gruppo con un solo click!',
    backgroundColor: '#F4B1BA',
  },
  {
    key: 'k3',
    title: 'DOVE E QUANDO VUOI',
    text: 'Scegli le date e i posti migliori!',
    backgroundColor: '#4093D2',
  },
  {
    key: 'k4',
    title: 'EVENTI DI QUALSIASI TIPO',
    text: 'Parita a calcetto? Sessione di studio? Grigliata in campagna? Organizziamo!',
    backgroundColor: '#644EE2',
  }
];

const styles = StyleSheet.create({
  backgroundContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  MainContainer: { 
   flex: 1, 
   paddingTop: 20, 
   alignItems: 'center', 
   justifyContent: 'center', 
   padding: 20 
  }, 
  text: {
    color: 'white',
    textAlign: 'center',
    fontSize: 20,
    fontWeight: 'bold'
},
button: {
  width: WIDTH - 55,
  height: 45,
  borderRadius: 45,
  backgroundColor: '#448AFF',
  justifyContent: 'center',
  marginTop: 50,
},
});