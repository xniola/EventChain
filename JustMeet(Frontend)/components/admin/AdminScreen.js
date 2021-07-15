import React from "react";
import {
StyleSheet,
View,
Text,
TouchableOpacity,
Dimensions,
ImageBackgrounds
} from "react-native";
import email from 'react-native-email'

export default class AdminScreen extends React.Component {

  handleEmail = () => {
    const to = [] // string or array of email addresses
    email(to, {
        // Optional additional arguments
        cc: [], // string or array of email addresses
        bcc: '', // string or array of email addresses
        subject: '',
        body: ''
    }).catch(console.error)
  }

  render(){
  
  return(
    <View style={styles.container}>
         <TouchableOpacity style={styles.button }
               onPress = {() => this.props.navigation.navigate("Lista User [Admin]")}>
              <Text style={styles.text}>Utenti</Text>
         </TouchableOpacity>
   
  
   
         <TouchableOpacity style={styles.button}
                            onPress = {() => this.props.navigation.navigate("Lista Eventi [Admin]")}>
              <Text style={styles.text}>Eventi</Text>
         </TouchableOpacity>
  
   
         <TouchableOpacity style={styles.button}
                                  onPress = {() => this.handleEmail()}>
                    <Text style={styles.text}>Invia Email</Text>
          </TouchableOpacity>

          <TouchableOpacity style={styles.button}
                                  onPress = {() => this.props.navigation.replace("Benvenuto")}>
                    <Text style={styles.text}>Logout</Text>
          </TouchableOpacity>
     </View>
  )
  }

}


  const { width: WIDTH } = Dimensions.get('window')


  const styles = StyleSheet.create({
        container: {
          flex: 1,
          backgroundColor: "#fff"
        }, 
        button: {
          alignSelf: 'center',
          alignItems: 'center',
          backgroundColor: '#DDDDDD',
          padding: 20,
          marginTop: 20,
          width: 300,
          borderRadius:50
        },
        text: {
          alignSelf: 'center',
          color: 'black',
          fontSize: 16,
          fontWeight: 'bold',
          paddingTop: 10,
          paddingBottom: 10 
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