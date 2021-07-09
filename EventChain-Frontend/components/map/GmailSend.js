import React from 'react'
import { StyleSheet, Button, View } from 'react-native'
import email from 'react-native-email'

export default class GmailSend extends React.Component {
    render() {
        return (
            <View style={styles.container}>
                {this.handleEmail()}
            </View>
        )
    }

    handleEmail = () => {
        const to = ['stefano.perniola@studenti.unicam.it'] // string or array of email addresses
        email(to, {
            // Optional additional arguments
            //cc: ['bazzy@moo.com', 'doooo@daaa.com'], // string or array of email addresses
            //bcc: 'mee@mee.com', // string or array of email addresses
            subject: 'SEGNALAZIONE PROBLEMI JUSTMEET',
            body: 'Segnala il tuo problema'
        }).catch(console.error)
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        alignItems: 'center',
        justifyContent: 'center'
    }
})