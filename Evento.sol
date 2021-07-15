//pragma solidity >=0.7.0 <0.9.0;
pragma solidity ^0.5.0;

contract EventManager {

    string public nome;

    uint public eventCount = 0; //Numero di eventi attuali
    mapping(uint => Evento) public eventi; 


    // Definisco la struttura di un evento
    struct Evento{
        uint id;
        string titolo_evento;
        string descrizione;
        uint16 posti_disponibili;
        uint prezzo;
        bool biglietti_esauriti;
        string data_evento;
        string luogo_evento;

        // Oppure: uint256 public data_evento;
        /* modo alternativo per definire il luogo dell' evento
        struct Luogo { 
            fixed latitudine;
            fixed longitudine;
        }
        */
        address payable organizzatore; // indirizzo dell'organizzatore dell'evento (a cui si paga)
    }

    // Crea un evento che verrà inviato alla Blockchain,
    // in modo che i nodi possano ascoltare (imparare) la creazione dell'evento
    event CreaEvento(
        uint id,
        string titolo_evento,
        string descrizione,
        uint16 posti_disponibili,
        uint prezzo,
        bool biglietti_esauriti,
        string data_evento,
        string luogo_evento,
        address payable organizzatore
    );

    // Prenota un evento che verrà inviato alla Blockchain,
    // in modo che i nodi possano ascoltare (imparare) la prenotazione dell'evento
    event PrenotaEvento(
        uint id,
        string titolo_evento,
        string descrizione,
        uint16 posti_disponibili,
        uint prezzo,
        bool biglietti_esauriti,
        string data_evento,
        string luogo_evento,
        address payable organizzatore
    ); 

    constructor() public { 
        nome = "EventChain";
    }

    //Funzione per creare un evento.
    function creaEvento(
        // Parametri in ingresso
        string titolo_evento,
        string descrizione,
        uint16 posti_disponibili,
        uint prezzo,
        // false (bool biglietti_esauriti),
        string data_evento,
        string luogo_evento)
            public {  
            require(
                // Mi assicuro che i parametri inseriti siano validi
                bytes(titolo_evento).length > 0 &&
                bytes(descrizione).length > 0 && 
                posti_disponibili > 0 &&
                prezzo > 0 &&
                bytes(data_evento).length > 0 &&
                bytes(luogo_evento).length > 0,
                "Parametri dell'evento non adeguati");
           
            // Aumentiamo il contatore degli eventi di 1
            eventCount++;
            // Aggiungo l'evento al "dizionario". 
            // msg.sender è l'indirizzo di chi ha chiamato questa funzione (organizzatore dell'evento)
            eventi[eventCount] = Evento(eventCount, titolo_evento, descrizione, posti_disponibili, prezzo, false, data_evento, luogo_evento, msg.sender);
            
            //Chiamiamo un evento che invierà una "transazione" alla blockchain
            emit CreaEvento(eventCount, titolo_evento, descrizione, posti_disponibili, prezzo, false, data_evento, luogo_evento, msg.sender);
    }	

    function get() public view returns (string memory) {
        return nome;
    }

    function set(string memory _nome) public {
        nome = _nome;
    }

    // Per convenzione indico con '_' (underscore) i parametri della funzione
    function prenotaEvento (uint _id) public payable {
        // Ottiengo l'evento dalla lista.
        // Si usa "memory" perché è una variabile di struttura (memorizzate per sempre nella blockchain)
        // la variabile memory verrà cancellata dopo l'esecuzione della funzione
    	Evento memory evento = eventi[_id];

    	//Ottengo l'indirizzo dell'organizzatore dell'evento
    	address payable organizzatore = evento.organizzatore;

    	// Controllo se l'evento ha un id valido
        // require richiede che la condizione tra parentesi sia soddisfatta, altrimenti la funzione terminerà in questa fase.
    	// TODO Forse c'è da mettere -- evento.id <= eventCount --- 
        require(evento.id > 0 && evento.id < eventCount, "Id dell'evento non valido");
    	
        // Controllo che è stato inviato denaro sufficiente 
        // msg.value è la quantità di ETH che è stata trasferita quando è stata chiamata la funzione
    	require(msg.value >= _evento.prezzo, "Denaro non sufficiente");

    	//Verifico che l'evento non abbia esaurito i posti disponibili
    	require(!evento.biglietti_esauriti,"Biglietti esauriti");

    	//Verifico che il proprietario dell'evento non acquisti un biglietto
    	require(organizzatore != msg.sender,"Non puoi prenotare, sei il proprietario dell'evento");


    	// Decremento di 1 il numero di posti disponibili dell'evento
    	evento.posti_disponibili--;

        // Se sono finiti i biglietti non si può più prenotare l'evento
        if( evento.posti_disponibili == 0) {   
            evento.biglietti_esauriti = true;
        }

    	// Aggiornamento dell'evento sulla lista
    	eventi[_id] = evento;

    	// Trasferiamento dei soldi all'organizzatore
    	address(organizzatore).transfer(msg.value);

    	// Invio la transazione alla blockchain
        // TODO forse come id bisogna mettere eventCount(?)
    	emit PrenotaEvento(evento.id, evento.titolo_evento, evento.descrizione,
                           evento.posti_disponibili, evento.prezzo, evento.biglietti_esauriti,
                           evento.data_evento, evento.luogo_evento, evento.organizzatore);
    }

    /*
    function getTitolo() public view returns (string memory){
        return titolo_evento;
    }

    function getDescrizione() public view returns (string memory){
        return descrizione;
    }

     function getPostiDisponibili() public view returns(uint16){
        return posti_disponibili;
    }

    function getPrezzo() public view returns(uint){
        return prezzo;
    }

    function isFull() public view returns(bool){
        return biglietti_esauriti;
    }


    function getData() public view returns (string memory){
        return data_evento;
    }
    
    function getLuogo() public view returns (string memory){
        return luogo_evento;
    }

    function getOrganizzatore() public view returns (address payable){
        return organizzatore;
    }
    */
}
