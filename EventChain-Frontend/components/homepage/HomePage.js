import React from 'react'
import { View, Text, Image, StyleSheet ,ImageBackground,Dimensions,TouchableOpacity} from 'react-native'


const { width: WIDTH } = Dimensions.get('window')

export default class HomePage extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
       id:  this.props.route.params.id,
       nome:  this.props.route.params.nome,
       photo:  this.props.route.params.photoUrl,
       email: this.props.route.params.email,
       fullName: this.props.route.params.fullName
      }
      this.props.navigation.navigate = this.props.navigation.navigate.bind(this);
  }

    render(){
      return (
        <View style = {styles.container}>
          <ImageBackground source={require("../images/Sfondo.png")} style={styles.image}>
        <View style={styles.card}>
           <Text style={styles.titleText}>JustMeet</Text>
        </View>


      <TouchableOpacity style={styles.button} onPress={() => this.props.navigation.navigate('Profilo Utente',{email: this.state.email, })}>
          <Text style = {styles.text}> Il mio profilo </Text>
      </TouchableOpacity>

      <TouchableOpacity style={styles.button} onPress={() => this.props.navigation.navigate('Google Maps')}>
          <Text style = {styles.text}> Vai alla mappa </Text>
      </TouchableOpacity>

      <TouchableOpacity style={styles.button} onPress={() => this.props.navigation.navigate('Eventi',{
            fullName: this.state.fullName,
            email: this.state.email,
            photo: this.state.photo
          })}>
          <Text style = {styles.text}> Scopri gli eventi </Text>
      </TouchableOpacity>

      <TouchableOpacity style={styles.button} onPress={() => this.props.navigation.navigate('Cerca Evento',{
            fullName: this.state.fullName,
            email: this.state.email
          })}>
          <Text style = {styles.text}> Cerca Eventi </Text>
      </TouchableOpacity>
        
      <TouchableOpacity style={styles.button} onPress={() => this.props.navigation.navigate('Scegli Luogo',{
              email: this.state.email
              })}>
          <Text style = {styles.text}> Crea un evento </Text>
      </TouchableOpacity>
      </ImageBackground>
   </View>
    );
    }

}


const styles = StyleSheet.create({ 
  tinyLogo: {
    width: 100,
    height: 100,
  },
  backgroundContainer: {
  flex: 1,
  justifyContent: 'center',
  alignItems: 'center',
},
image: {
  flex: 1,
  resizeMode: "cover",
  justifyContent: "center",
  alignItems: 'center',
  width: '100%',
  height: '100%'
},
button: {
  alignItems: 'center',
  backgroundColor: 'rgba(142, 68, 173, 0.2)',
  padding: 20,
  marginTop: 20,
  width: 300,
  borderRadius:50
},
  card: {
    flex: 0.9,
    alignItems: 'center',
    alignContent: 'center',
    justifyContent: 'center'
  },
  background:{
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  header: {
    color: 'white',
    fontSize: 25
  },
  text: {
    color: 'black',
    textAlign: 'center',
    fontSize: 20,
    fontWeight: 'bold'
  },
  container: {
    flex: 1,
    backgroundColor: 'rgba(142, 68, 173, 0.5)',
    alignItems: "center",
    justifyContent: "center"
  },
  titleText: {
    fontSize: 40,
    fontWeight: "bold"
  }
});
