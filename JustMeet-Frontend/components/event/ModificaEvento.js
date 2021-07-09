import React from 'react'
import {Text,StyleSheet,Dimensions,TextInput,TouchableOpacity,ScrollView} from 'react-native';
import RNPickerSelect from 'react-native-picker-select';


export default class CreazioneEvento extends React.Component {
  constructor(props){
    super(props);
    this.state = { 
      title: '',
      description: '',
      organizzatore: '',
      numPartecipanti: '',
      topic: ''
    };
  }


 handleTitle = (text) => {
    this.setState({ title: text })
 }

 handleDescription = (text) => {
   this.setState({description: text})
 }

handleOrganizzatore = (text) => {
  this.setState({organizzatore: text})
}

handleNumPartecipanti = (text) => {
  this.setState({numPartecipanti: text})
}

  render() {
    return (
            <ScrollView style={styles.inputContainer}>

                <TextInput 
                    style={styles.input}
                    placeholder={'Nome'}
                    placeholderTextColor={'grey'}
                    underlineColorAndroid='transparent'
                    value = {this.state.title}
                    onChangeText = {this.handleTitle}
                />
                <TextInput 
                    style={styles.input}
                    placeholder={'Descrizione'}
                    placeholderTextColor={'grey'}
                    underlineColorAndroid='transparent'
                    value = {this.state.description}
                    onChangeText = {this.handleDescription}
                />
              

                <TextInput 
                    style={styles.input}
                    placeholder={'Organizzatore'}
                    placeholderTextColor={'grey'}
                    underlineColorAndroid='transparent' 
                    value = {this.state.organizzatore}
                    onChangeText = {this.handleOrganizzatore}
                />

                <TextInput 
                    style={styles.input}
                    placeholder={'Numero Partecipanti'}
                    placeholderTextColor={'grey'}
                    underlineColorAndroid='transparent' 
                    value = {this.state.numPartecipanti}
                    onChangeText = {this.handleNumPartecipanti}
                />     

          <Text style={styles.text}>
            Scegli Categoria
          </Text>
                  <RNPickerSelect
                    style={styles.form}
                    onValueChange={(value) => this.setState({topic: value})}
                    items={[
                        { label: 'Studio', value: '1' },
                        { label: 'Amici', value: '2' },
                        { label: 'Sport', value: '3' },
                        { label: 'Party', value: '4' },
                        { label: 'Generale', value: '5' },
                          ]}
                      />

 
             <TouchableOpacity
             disabled={(this.state.title == '' || this.state.description == '' || this.state.organizzatore == ''
             || this.state.numPartecipanti == '' || this.state.topic == '')}
              style={styles.button}
              onPress = {() => 
                this.props.navigation.navigate('PutEvento',{
                  id: this.props.route.params.idEvento,
                  title: this.state.title,
                  description: this.state.description,
                  topic: this.state.topic,
                  organizzatore: this.state.organizzatore,
                  numPartecipanti: this.state.numPartecipanti
                })
            } >
                <Text style = {styles.text}> Modifica </Text>
              </TouchableOpacity>

              <Text style={styles.warning}>N.B. Potrai continuare solo se compili tutto</Text>
              
              </ScrollView>
              
           
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
      marginBottom: 25
  },
  inputContainer: {
      marginBottom: 20,
      marginTop: 20
  },
  btnPubblica: {
    width: WIDTH - 55,
    height: 45,
    borderRadius: 45,
    backgroundColor: '#448AFF',
    justifyContent: 'center',
    marginTop: 50,
},
button: {
  alignItems: 'center',
  backgroundColor: '#DDDDDD',
  padding: 20,
  marginTop: 20
},
text: {
  color: 'black',
  textAlign: 'center',
  fontSize: 20,
  fontWeight: 'bold'
},
form: {
  marginTop: 20,
  marginBottom: 20
},
warning:{
  fontSize: 15,
  color: 'red',
  textAlign: 'center',
}
})