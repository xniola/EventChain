import React from "react";
import {StyleSheet,View, Text,TouchableOpacity,ImageBackground} from "react-native"; 


export default class SceltaEventiCreati extends React.Component {
    render(){
        return(
            <View style={styles.container}>
               <ImageBackground source={require("../images/Sfondo.png")} style={styles.image}>
                <TouchableOpacity style={styles.button} onPress={() => this.props.navigation.navigate('Eventi Creati',{
                    email: this.props.route.params.email
                    })}>
                     <Text style = {styles.text}> Lista Eventi </Text>
                </TouchableOpacity>

                <TouchableOpacity style={styles.button} onPress={() => this.props.navigation.navigate('Eventi Creati [MAPPA]',{
                    email: this.props.route.params.email
                    })}>
                     <Text style = {styles.text}> Mappa Eventi </Text>
                </TouchableOpacity>
                </ImageBackground>
            </View>
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