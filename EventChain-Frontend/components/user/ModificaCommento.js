import React from 'react'
import { View, Text,Dimensions,StyleSheet,TextInput,TouchableOpacity,ImageBackground} from 'react-native';

export default class ModificaCommento extends React.Component {
    constructor(props){
        super(props);
        this.state = { 
          body: ''
        };
      }
    
    
     handleBody = (text) => {
        this.setState({ body: text })
     }


    render(){
        return(
          <ImageBackground source={require("../images/Sfondo.png")} style={styles.image}>
            <View>
                <TextInput 
                    style={styles.input}
                    placeholder={'Scrivi commento...'}
                    placeholderTextColor={'grey'}
                    underlineColorAndroid='transparent' 
                    value = {this.state.body}
                    onChangeText = {this.handleBody}
                />
            

                <TouchableOpacity
                disabled={(this.state.body == '')}
                 style={styles.button}
                 onPress = {() => 
                   this.props.navigation.navigate('PutCommento',{
                     body: this.state.body,
                     id: this.props.route.params.id
                   })
               } >
                   <Text style = {styles.text}>Modifica Commento</Text>
                 </TouchableOpacity>
            </View>
            </ImageBackground>
        )
    }
}

const { width: WIDTH } = Dimensions.get('window')


const styles = StyleSheet.create({
    input: {
        width: WIDTH - 55,
        height: 45,
        borderRadius: 45,
        fontSize: 16,
        paddingLeft: 45,
        backgroundColor: 'white',
        color: 'black',
        marginHorizontal: 25,
        marginBottom: 25,
        marginTop:20
    },
    image: {
      flex: 1,
      resizeMode: "cover",
      justifyContent: "center",
      alignItems: 'center',
      width: '100%',
      height: '100%'
    },
    button: {
      alignSelf: 'center',
      alignItems: 'center',
      backgroundColor: 'rgba(142, 68, 173, 0.2)',
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
  }
})