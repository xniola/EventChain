import React from 'react'
import {TouchableOpacity,View,Text,ImageBackground,StyleSheet} from "react-native";

export default class PubblicaCommento extends React.Component {
    componentDidMount(){
        fetch('http://192.168.1.9:8080/users/'+this.props.route.params.email+'/comments/'+this.props.route.params.title,{
          method: 'POST',
          headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            body: this.props.route.params.body,
            photo: this.props.route.params.photo,
            idEvento: this.props.route.params.idEvento
          })
        })
    }

render(){
    return (
      <ImageBackground source={require("../images/Sfondo.png")} style={styles.image}>
        <View>
             <TouchableOpacity 
                style = {styles.button}
                onPress={() =>{this.props.navigation.navigate("Home Page")}}>
                <Text style = {styles.text}>Commento Pubblicato!</Text>
              </TouchableOpacity>
        </View>
      </ImageBackground>
    )

}
}


const styles = StyleSheet.create({ 
image: {
  flex: 1,
  resizeMode: "cover",
  justifyContent: "center",
  alignItems: 'center',
  width: '100%',
  height: '100%'
},
  text: {
    color: 'black',
    textAlign: 'center',
    fontSize: 20,
    fontWeight: 'bold'
  },
  button: {
    alignSelf: 'center',
    alignItems: 'center',
    backgroundColor: 'rgba(142, 68, 173, 0.2)',
    padding: 20,
    marginTop: 20,
    width: 300,
    borderRadius:50
  }
});
