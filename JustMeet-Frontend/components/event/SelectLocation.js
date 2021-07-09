import React from 'react';
import { TouchableOpacity, Text, ImageBackground, StyleSheet, View,TextInput ,Dimensions} from 'react-native';
import * as Location from 'expo-location';
import * as Permissions from 'expo-permissions';



export default class SelectLocation extends React.Component {
  constructor(props){
    super(props);
    this.state = { 
      indirizzo: '',
      result: '',
      inProgress: false,
      canGo: false
    };
  }
  
  componentDidMount() {
    Permissions.askAsync(Permissions.LOCATION);
  }

  _attemptGeocodeAsync = async (text) => {
    this.setState({ inProgress: true, error: null });
    try {
      let result = await Location.geocodeAsync(this.state.indirizzo);
      this.setState({ result });
    } catch (e) {
      this.setState({ error: e.message });
    } finally {
      this.setState({ inProgress: false });
      if(this.state.result.length > 0){
        this.setState({canGo: true})
        alert("Location trovata, puoi proseguire")
    }
    else{
      alert("Location non trovata, controlla l'input")
    }
  }
}

  handleIndirizzo = (text) => {
    this.setState({ indirizzo: text })
 }


  render() {
    return (
      <ImageBackground source={require("../images/Sfondo.png")} style={styles.image}>
      <View>
          <TextInput 
            style={styles.input}
            placeholder={'Nome Luogo'}
            placeholderTextColor={'grey'}
            underlineColorAndroid='transparent'
            value = {this.state.indirizzo}
            onChangeText = {this.handleIndirizzo}
            />
            
          <TouchableOpacity
            style={styles.button}
            onPress={this._attemptGeocodeAsync}
            title="Verifica">
            <Text>Verifica</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={styles.button}
            disabled={!this.state.canGo}
            onPress = {() => this.props.navigation.navigate("Scegli Data",{
                email: this.props.route.params.email,
                nomeLuogo: this.state.indirizzo,
                latitude: this.state.result[0].latitude,
                longitude: this.state.result[0].longitude 
            })} >
            <Text style={styles.text}> Prosegui </Text>
          </TouchableOpacity>
      </View>
      </ImageBackground>
    );
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
    marginTop: 100
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
});