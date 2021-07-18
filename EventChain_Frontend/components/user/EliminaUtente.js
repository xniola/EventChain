import React from 'react'
import {
    Button,
    Alert,
    Dimensions,
    StyleSheet,
    View,
    ActivityIndicator,
    FlatList,
    Text,
    TouchableOpacity
    } from "react-native";

export default class EliminaUtente extends React.Component{
    render(){
        return(
            <View style={styles.container}>
                <Text style={styles.text2}>
                    Sei sicuro di voler eliminare il tuo account?
                </Text>
                <TouchableOpacity 
                style={styles.button} 
                onPress={() => this.props.navigation.navigate("Elimina Definitivo",{
                  email: this.props.route.params.email
                })}>
                  <Text style = {styles.text}> Conferma </Text>
                </TouchableOpacity>
                <Text style={styles.text2}>
                   N.B. L'operazione è irreversibile
                </Text>
            </View>
        )
    }

} 


const styles = StyleSheet.create({
    container: {
      marginTop: 250,
      marginBottom: 250,
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
        color: 'black',
        textAlign: 'center',
        fontSize: 20,
        fontWeight: 'bold'
      },
      text2:{
        fontSize: 15,
        textAlign: 'center',
      }
  });