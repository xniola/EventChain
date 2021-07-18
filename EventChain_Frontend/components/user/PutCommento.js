import React from 'react'
import { View,StyleSheet ,Text,TouchableOpacity,ImageBackground} from 'react-native';

export default class PutCommento extends React.Component{
    componentDidMount(){
        return fetch('http://192.168.1.9:8080/comments/'+this.props.route.params.id, {
            method: 'PUT',
            headers: {
              Accept: 'application/json',
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({
              body: this.props.route.params.body
            }),
          });
    }


    render(){
      return(
        <ImageBackground source={require("../images/Sfondo.png")} style={styles.image}>
          <View>
               <TouchableOpacity 
              style={styles.button} 
              onPress={() => {this.props.navigation.navigate("Profilo Utente")}}>
                <Text style = {styles.text}>  Commento Modificato! </Text>
              </TouchableOpacity>
          </View>
          </ImageBackground>
      )
  }
}

const styles = StyleSheet.create({
  button: {
    alignItems: 'center',
    backgroundColor: 'rgba(142, 68, 173, 0.2)',
    padding: 20,
    marginTop: 20,
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