import React from 'react'
import {
    Dimensions,
    StyleSheet,
    View,
    ImageBackground,
    Text,
    TouchableOpacity
    } from "react-native";

export default class CercaEvento extends React.Component{
    render(){
        return(
          <ImageBackground source={require("../images/Sfondo.png")} style={styles.image}>
                <TouchableOpacity style={styles.button }
                      onPress = {() => this.props.navigation.navigate('Input Topic',{
                        fullName: this.props.route.params.fullName,
                        email: this.props.route.params.email
                      })}>
                     <Text style={styles.text}>Topic</Text>
                </TouchableOpacity>
        
            <View style={{borderLeftWidth: 1,borderLeftColor: 'white'}}/>
            <View style={{ flex: 1}}>
                <TouchableOpacity style={styles.button}
                                   onPress = {() => this.props.navigation.navigate('Lista Eventi Più Vicini',{
                                    fullName: this.props.route.params.fullName,
                                    email: this.props.route.params.email
                                   })}>
                     <Text style={styles.text}>Location</Text>
                </TouchableOpacity>
            </View>
            </ImageBackground>
        )
    }
}

const { width: WIDTH } = Dimensions.get('window')

const styles = StyleSheet.create({
  image: {
    flex: 1,
    resizeMode: "cover",
    justifyContent: "center",
    alignItems: 'center',
    width: '100%',
    height: '100%'
   },
    container: {
      flex: 1,
      backgroundColor: "#fff"
    },
    loader:{
      flex: 1,
      justifyContent: "center",
      alignItems: "center",
      backgroundColor: "#fff"
    },
    list:{
      paddingVertical: 4,
      margin: 5,
      backgroundColor: "#fff"
    },
    button: {
      alignItems: 'center',
      backgroundColor: 'rgba(142, 68, 173, 0.2)',
      padding: 20,
      marginTop: 20,
      width: 300,
      borderRadius:50
    },
    buttonInfo: {
      width: WIDTH - 55,
      backgroundColor: '#448AFF',
      alignItems: 'flex-end',
    },
    text: {
      color: 'black',
      textAlign: 'center',
      fontSize: 20,
      fontWeight: 'bold'
    },
    instructions: {
      fontSize: 16,
      textAlign: 'center',
      color: '#333333',
      marginBottom: 5,
      marginTop: 250
    },
    image: {
      flex: 1,
      resizeMode: "cover",
      justifyContent: "center",
      alignItems: 'center',
      width: '100%',
      height: '100%'
    }
  });