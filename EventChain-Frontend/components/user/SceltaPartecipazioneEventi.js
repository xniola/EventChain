import React from "react";
import {StyleSheet,View, Text,TouchableOpacity,ImageBackground} from "react-native"; 


export default class SceltaPartecipazioneEventi extends React.Component {
    render(){
        return(
          <ImageBackground source={require("../images/Sfondo.png")} style={styles.image}>
            <View style={styles.container}>
                <TouchableOpacity style={styles.button} onPress={() => this.props.navigation.navigate('Partecipazione a Eventi',{
                    fullName: this.props.route.params.fullName,
                    email: this.props.route.params.email
                    })}>
                     <Text style = {styles.text}> Lista Eventi </Text>
                </TouchableOpacity>

                <TouchableOpacity style={styles.button} onPress={() => this.props.navigation.navigate('Partecipazioni [MAPPA]',{
                    email: this.props.route.params.email
                    })}>
                     <Text style = {styles.text}> Mappa Eventi </Text>
                </TouchableOpacity>
            </View>
          </ImageBackground>
        )
    }
  
}

const styles = StyleSheet.create({ 
 
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
      flex: 0.9,
      alignItems: "center",
      justifyContent: "center"
    },
    button: {
      alignSelf: 'center',
      alignItems: 'center',
      //backgroundColor: '#DDDDDD',
      backgroundColor: 'rgba(142, 68, 173, 0.2)',
      padding: 20,
      marginTop: 20,
      width: 300,
      borderRadius:50
    },
    titleText: {
      fontSize: 40,
      fontWeight: "bold"
    }
  });