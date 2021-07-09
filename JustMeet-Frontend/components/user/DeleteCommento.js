import React from 'react'
import {View,StyleSheet,TouchableOpacity,Text,ImageBackground} from 'react-native'

export default class DeleteCommento extends React.Component{
    componentDidMount(){
        fetch('http://192.168.1.9:8080/comments/'+this.props.route.params.id, {
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
              <ImageBackground source={require("../images/Sfondo.png")} style={styles.image}>
                 <TouchableOpacity 
                style={styles.button} 
                onPress={() => {this.props.navigation.navigate("Home Page")}}>
                  <Text style = {styles.text}> Fatto </Text>
                </TouchableOpacity>
              </ImageBackground>
            </View>
        )
    }
}

const styles = StyleSheet.create({
button: {
    alignSelf: 'center',
    alignItems: 'center',
    backgroundColor: '#DDDDDD',
    padding: 20,
    marginTop: 275,
    width: 300,
    borderRadius:50
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