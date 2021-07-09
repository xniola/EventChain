import React from 'react'
import RNPickerSelect from 'react-native-picker-select';
import { Button,TouchableOpacity,View,Text, Alert,StyleSheet,ImageBackground } from 'react-native';
 

const scegliMese = new String("Scegli il mese");

export default class MyDatePicker extends React.Component {
  constructor(props){
    super(props);
    this.state = { 
        anno: '',
        giorno: '',
        mese: '',
        ore: '',
        minuti: ''
      
    }
  }

  handleMese = (value) => {
    this.setState({ mese: value })
 }

  render(){
    return (
      <ImageBackground source={require("../images/Sfondo.png")} style={styles.image}>
      <View style={styles.form}>
        <Text style = {styles.text2}>
          Scegi Giorno
        </Text>
        <RNPickerSelect
            style={styles.form}
            onValueChange={(value) => this.setState({giorno: value})}
            items={[
                { label: '1', value: '01' },{ label: '2', value: '02' },{ label: '3', value: '03' },
                { label: '4', value: '04' },{ label: '5', value: '05' },{ label: '6', value: '06' },
                { label: '7', value: '07' },{ label: '8', value: '08' },{ label: '9', value: '09' },
                { label: '10', value: '10' },{ label: '11', value: '11' },{ label: '12', value: '12' },
                { label: '13', value: '13' },{ label: '14', value: '14' },{ label: '15', value: '15' },
                { label: '16', value: '16' },{ label: '17', value: '17' },{ label: '18', value: '18' },
                { label: '19', value: '19' },{ label: '20', value: '20' },{ label: '21', value: '21' },
                { label: '22', value: '22' },{ label: '23', value: '23' },{ label: '24', value: '24' },
                { label: '25', value: '25' },{ label: '26', value: '26' },{ label: '27', value: '27' },
                { label: '28', value: '28' },{ label: '29', value: '29' },{ label: '30', value: '30' },
                { label: '31', value: '31' },
            ]}
        />

          <Text style = {styles.text2}>
            Scegli mese
          </Text>
        <RNPickerSelect
            style={styles.form}
            onValueChange={(value) => this.setState({mese: value})}
            items={[
                { label: 'Gennaio', value: '01' },
                { label: 'Febbraio', value: '02' },
                { label: 'Marzo', value: '03' },
                { label: 'Aprile', value: '04' },
                { label: 'Maggio', value: '05' },
                { label: 'Giugno', value: '06' },
                { label: 'Luglio', value: '07' },
                { label: 'Agosto', value: '08' },
                { label: 'Settembre', value: '09' },
                { label: 'Ottobre', value: '10' },
                { label: 'Novembre', value: '11' },
                { label: 'Dicembre', value: '12' },
            ]}
        />

          <Text style = {styles.text2}>
            Scegli ore
          </Text>
        <RNPickerSelect
            style={styles.form}
            onValueChange={(value) => this.setState({ore: value})}
            items={[
                { label: '00', value: '00' },{ label: '01', value: '01' },
                { label: '02', value: '02' },{ label: '03', value: '03' },
                { label: '04', value: '04' },{ label: '05', value: '05' },
                { label: '06', value: '06' },{ label: '07', value: '07' },
                { label: '08', value: '08' },{ label: '09', value: '09' },
                { label: '10', value: '10' },{ label: '11', value: '11' }, 
                { label: '12', value: '12' },{ label: '13', value: '13' },
                { label: '14', value: '14' },{ label: '15', value: '15' },
                { label: '16', value: '16' },{ label: '17', value: '17' },
                { label: '18', value: '18' },{ label: '19', value: '19' },
                { label: '20', value: '20' },{ label: '21', value: '21' },
                { label: '22', value: '22' },{ label: '23', value: '23' },
                ]}
        />


          <Text style = {styles.text2}>
            Scegli minuti
          </Text>
        <RNPickerSelect
            style={styles.form}
            onValueChange={(value) => this.setState({minuti: value})}
            items={[
                { label: '00', value: '00' },{ label: '01', value: '01' },{ label: '02', value: '02' },{ label: '03', value: '03' },
                { label: '04', value: '04' },{ label: '05', value: '05' },{ label: '06', value: '06' },{ label: '07', value: '07' },
                { label: '08', value: '08' },{ label: '09', value: '09' },{ label: '10', value: '10' },{ label: '11', value: '11' }, 
                { label: '12', value: '12' },{ label: '13', value: '13' }, { label: '14', value: '14' },{ label: '15', value: '15' },
                { label: '16', value: '16' },{ label: '17', value: '17' },{ label: '18', value: '18' },{ label: '19', value: '19' },
                { label: '20', value: '20' },{ label: '21', value: '21' },{ label: '22', value: '22' },{ label: '23', value: '23' },
                { label: '24', value: '24' },{ label: '25', value: '25' },{ label: '26', value: '26' },{ label: '27', value: '27' },
                { label: '28', value: '28' },{ label: '29', value: '29' },{ label: '30', value: '30' },{ label: '31', value: '31' },
                { label: '32', value: '32' },{ label: '33', value: '33' },{ label: '34', value: '34' },{ label: '35', value: '35' },
                { label: '36', value: '36' },{ label: '37', value: '37' },{ label: '38', value: '38' },{ label: '39', value: '39' },
                { label: '40', value: '40' },{ label: '41', value: '41' },{ label: '42', value: '42' },{ label: '43', value: '43' },
                { label: '44', value: '44' },{ label: '45', value: '45' },{ label: '46', value: '46' },{ label: '47', value: '47' },
                { label: '48', value: '48' },{ label: '49', value: '49' },{ label: '50', value: '50' },{ label: '51', value: '51' },
                { label: '52', value: '52' },{ label: '53', value: '53' },{ label: '54', value: '54' },{ label: '55', value: '55' },
                { label: '56', value: '56' },{ label: '57', value: '57' },{ label: '58', value: '58' },{ label: '59', value: '59' },
                ]}
        />

          <Text style = {styles.text2}>
            Scegli Anno
          </Text>
        <RNPickerSelect
            style={styles.form}
            onValueChange={(value) => this.setState({anno: value})}
            items={[
                { label: '2020', value: '2020' },
                { label: '2021', value: '2021' },
                { label: '2022', value: '2022' },
                { label: '2023', value: '2023' },
                { label: '2024', value: '2024' },
                { label: '2025', value: '2025' },
            ]}
        />



        <TouchableOpacity style={styles.button}
          disabled={(this.state.giorno == '' || this.state.mese == '' || this.state.anno == ''
                  || this.state.ore == '' || this.state.minuti == '')}
                           onPress={() => this.props.navigation.navigate("Crea un evento",{
                             email: this.props.route.params.email,
                             nome: this.props.route.params.nomeLuogo,
                             latitude: this.props.route.params.latitude,
                             longitude: this.props.route.params.longitude,
                             anno: this.state.anno,
                             giorno: this.state.giorno,
                             mese: this.state.mese,
                             ore: this.state.ore,
                             minuti: this.state.minuti
                           })}>
          <Text style={styles.text}>Fatto </Text>
      </TouchableOpacity>

      <Text style={styles.warning}>N.B.Potrai continuare solo se compili tutto</Text>
      </View>
      </ImageBackground>
    )
  };
}

const styles = StyleSheet.create({
  text2:{
    fontSize: 15,
    color: 'black',
    textAlign: 'center',
    marginBottom: 10,
    marginTop: 10
  },
  text: {
    color: 'black',
    textAlign: 'center',
    fontSize: 20,
    fontWeight: "bold"
  },
  form: {
    marginBottom: 30,
    marginTop: 30,
    
  },
  warning:{
    fontSize: 15,
    color: 'rgba(142, 68, 173, 0.6)',
    textAlign: 'center',
  },
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
  },
})