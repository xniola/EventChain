import React from 'react'
import { View, Text,ImageBackground,StyleSheet,TouchableOpacity} from 'react-native';

export default class AnnullaEvento extends React.Component {
    componentDidMount(){
        fetch('http://192.168.1.9:8080/events/'+this.props.route.params.idEvento, {
            method: 'DELETE',
            headers: {
              Accept: 'application/json',
              'Content-Type': 'application/json',
            },
          });
    }


render(){
    return (
      <ImageBackground source={require("../images/Sfondo.png")} style={styles.image}>
        <View>    
             <TouchableOpacity
                style = {styles.button}
                onPress={() => this.props.navigation.navigate("Home Page")}>
                  <Text style = {styles.text}>Evento Annullato!</Text>
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
 alignItems: 'center',
 backgroundColor: 'rgba(142, 68, 173, 0.2)',
 padding: 20,
 marginTop: 20,
 width: 300,
 borderRadius:50
}
});