import React from 'react'
import { StyleSheet, Text, View, TouchableOpacity,Dimensions,ImageBackground, Alert } from "react-native"
import * as Google from 'expo-google-app-auth'

export default class LoginPage extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      signedIn: false,
      id: "",
      email: "",
      cognome: "",
      nome: "",
      photoUrl: "",
      fullName: ""
    }
  }

  addUser = () => {
    fetch('http://192.168.1.9:8080/users', {
          method: 'POST',
          headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            nome: this.state.nome,
            cognome: this.state.cognome,
            email: this.state.email,
            photoUrl: this.state.photoUrl
          }),
        })   
  }

  checkUser = () => {
    this.state.signedIn ? ( 
      this.state.email === "stefano.perniola@studenti.unicam.it" ? (
      this.props.navigation.replace("Admin")
      ) :
        this.props.navigation.replace("Home Page",{
            nome: this.state.nome,
            cognome: this.state.cognome,
            id: this.state.id,
            photoUrl: this.state.photoUrl,
            email: this.state.email,
            fullName: this.state.fullName,
        })
    ) : (this.signIn())
  }
  


  signIn = async () => {
    try {
      const result = await Google.logInAsync({
        androidClientId:
          "82869702994-obb654jdvpuo53p59t6ko94j95fttgql.apps.googleusercontent.com",
        scopes: ["profile", "email"]
      })

      if (result.type === "success") {
        this.setState({
          signedIn: true,
          id: result.user.id,
          cognome: result.user.familyName,
          email: result.user.email,
          nome: result.user.givenName,
          photoUrl: result.user.photoUrl,
          fullName: result.user.name
        })
        this.addUser()
        this.checkUser()
      } else {
        console.log("cancelled")
      }
    } catch (e) {
      console.log("error", e)
    } 
}



    render(){
    return (
      <ImageBackground source={require("../images/Sfondo.png")} style={styles.image}>
        <View>
          <Text style={styles.title}>
            Benvenuto in JustMeet!
          </Text>

          <TouchableOpacity style = {styles.button}
            onPress = {() => Alert.alert("Informativa","Con JustMeet puoi creare e prendere parte a eventi vicino "+
            "a te. L'idea è quella di semplificare il processo organizzativo di un evento di qualsiasi dimensione e tipologia "+
            "con pochi step "+
            "e in maniera molto semplice!\n\n"+
            "Dati raccolti\n\n"+ 
            "Profilo: email e foto Google, ai soli fini dell'applicazione\n\n"+
            "Tracciamento posizione: per migliorare l'esperienza di navigazione sulla mappa\n\n"+
            "Info di contatto:\n stefano.perniola@studenti.unicam.it\n\n"+
            "La tutela della leggitimità degli eventi è importante, un Moderatore sarà incaricato di rimuovere eventi illeggittimi o utenti che non rispettano le buone norme di comportamento.\n\n"+
            "Per andare avanti bisogna effettuare la login con Google")}>
            <Text style={styles.text}>Informativa App</Text>
          </TouchableOpacity>

          <TouchableOpacity style={styles.button}  onPress={() => this.signIn()} >
            <Text style = {styles.text}>Accedi con Google </Text>
          </TouchableOpacity>

       </View>
       </ImageBackground>
      )
    }
}

const { width: WIDTH } = Dimensions.get('window')

const styles = StyleSheet.create({
  title: {
    fontSize: 31,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    fontSize: 16,
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
  button: {
    alignSelf: 'center',
    alignItems: 'center',
    backgroundColor: 'rgba(142, 68, 173, 0.2)',
    padding: 20,
    marginTop: 20,
    width: 300,
    borderRadius:50
  },
  text: {
    color: 'black',
    textAlign: 'center',
    fontSize: 20,
    fontWeight: 'bold'
  },
  image: {
    flex: 1,
    resizeMode: "cover",
    justifyContent: "center",
    alignItems: 'center',
    width: '100%',
    height: '100%'
  }
})