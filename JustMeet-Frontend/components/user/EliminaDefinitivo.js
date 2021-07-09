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

export default class EliminaDefinitivo extends React.Component{
    componentDidMount(){
        fetch('http://192.168.1.9:8080/users/'+this.props.route.params.email, {
            method: 'DELETE',
              headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
              },
        });
    }

    render(){
        return(
            <View>
                {this.props.navigation.replace("Benvenuto")}
            </View>
        )
    }
}