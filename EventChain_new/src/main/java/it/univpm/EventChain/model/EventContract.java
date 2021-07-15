package it.univpm.EventChain.model;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.ens.EnsResolver;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class EventContract extends Contract {
	
	
    protected EventContract(EnsResolver ensResolver, String contractBinary, String contractAddress, Web3j web3j,
			TransactionManager transactionManager, ContractGasProvider gasProvider) {
		super(ensResolver, contractBinary, contractAddress, web3j, transactionManager, gasProvider);
	}

	public static final String BINARY = "608060405260006001553480156200001657600080fd5b506040518060400160405280600a81526020017f4576656e74436861696e0000000000000000000000000000000000000000000081525060009080519060200190620000649291906200006b565b5062000180565b82805462000079906200011b565b90600052602060002090601f0160209004810192826200009d5760008555620000e9565b82601f10620000b857805160ff1916838001178555620000e9565b82800160010185558215620000e9579182015b82811115620000e8578251825591602001919060010190620000cb565b5b509050620000f89190620000fc565b5090565b5b8082111562000117576000816000905550600101620000fd565b5090565b600060028204905060018216806200013457607f821691505b602082108114156200014b576200014a62000151565b5b50919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b61130e80620001906000396000f3fe6080604052600436106100705760003560e01c80636d4ce63c1161004e5780636d4ce63c1461010b57806371be2e4a14610136578063bedd5ebd14610161578063f68fe0e31461017d57610070565b8063219024a1146100755780632deb124b146100b75780634ed3885e146100e2575b600080fd5b34801561008157600080fd5b5061009c60048036038101906100979190610c52565b6101a6565b6040516100ae96959493929190610e80565b60405180910390f35b3480156100c357600080fd5b506100cc6102a5565b6040516100d99190610da3565b60405180910390f35b3480156100ee57600080fd5b5061010960048036038101906101049190610b86565b610333565b005b34801561011757600080fd5b5061012061034d565b60405161012d9190610da3565b60405180910390f35b34801561014257600080fd5b5061014b6103df565b6040516101589190610e65565b60405180910390f35b61017b60048036038101906101769190610c52565b6103e5565b005b34801561018957600080fd5b506101a4600480360381019061019f9190610bcf565b610853565b005b60026020528060005260406000206000915090508060000154908060010180546101cf9061101c565b80601f01602080910402602001604051908101604052809291908181526020018280546101fb9061101c565b80156102485780601f1061021d57610100808354040283529160200191610248565b820191906000526020600020905b81548152906001019060200180831161022b57829003601f168201915b5050505050908060020160009054906101000a900461ffff16908060030154908060040160009054906101000a900460ff16908060040160019054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905086565b600080546102b29061101c565b80601f01602080910402602001604051908101604052809291908181526020018280546102de9061101c565b801561032b5780601f106103005761010080835404028352916020019161032b565b820191906000526020600020905b81548152906001019060200180831161030e57829003601f168201915b505050505081565b8060009080519060200190610349929190610a34565b5050565b60606000805461035c9061101c565b80601f01602080910402602001604051908101604052809291908181526020018280546103889061101c565b80156103d55780601f106103aa576101008083540402835291602001916103d5565b820191906000526020600020905b8154815290600101906020018083116103b857829003601f168201915b5050505050905090565b60015481565b6000600260008381526020019081526020016000206040518060c00160405290816000820154815260200160018201805461041f9061101c565b80601f016020809104026020016040519081016040528092919081815260200182805461044b9061101c565b80156104985780601f1061046d57610100808354040283529160200191610498565b820191906000526020600020905b81548152906001019060200180831161047b57829003601f168201915b505050505081526020016002820160009054906101000a900461ffff1661ffff1661ffff168152602001600382015481526020016004820160009054906101000a900460ff161515151581526020016004820160019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681525050905060008160a001519050600082600001511180156105615750600154826000015111155b6105a0576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161059790610de5565b60405180910390fd5b81606001513410156105e7576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016105de90610e45565b60405180910390fd5b81608001511561062c576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161062390610e05565b60405180910390fd5b3373ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16141561069b576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161069290610e25565b60405180910390fd5b8160400180518091906106ad90610ff2565b61ffff1661ffff16815250506000826040015161ffff1614156106dc5760018260800190151590811515815250505b8160026000858152602001908152602001600020600082015181600001556020820151816001019080519060200190610716929190610a34565b5060408201518160020160006101000a81548161ffff021916908361ffff1602179055506060820151816003015560808201518160040160006101000a81548160ff02191690831515021790555060a08201518160040160016101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055509050508073ffffffffffffffffffffffffffffffffffffffff166108fc349081150290604051600060405180830381858888f193505050501580156107f4573d6000803e3d6000fd5b507ff8be699982b68b6c88b9d28fd4c31268142cef71899293e7727aec062d269739826000015183602001518460400151856060015186608001518760a0015160405161084696959493929190610e80565b60405180910390a1505050565b60008451118015610868575060008361ffff16115b80156108745750600082115b6108b3576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016108aa90610dc5565b60405180910390fd5b600160008154809291906108c69061107f565b91905055506040518060c0016040528060015481526020018581526020018461ffff1681526020018381526020016000151581526020018273ffffffffffffffffffffffffffffffffffffffff16815250600260006001548152602001908152602001600020600082015181600001556020820151816001019080519060200190610952929190610a34565b5060408201518160020160006101000a81548161ffff021916908361ffff1602179055506060820151816003015560808201518160040160006101000a81548160ff02191690831515021790555060a08201518160040160016101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055509050507f7576b0673f80282f3470aa12c665268a7ef199efb4b683e1486f96f8cf14e65c600154858585600086604051610a2696959493929190610e80565b60405180910390a150505050565b828054610a409061101c565b90600052602060002090601f016020900481019282610a625760008555610aa9565b82601f10610a7b57805160ff1916838001178555610aa9565b82800160010185558215610aa9579182015b82811115610aa8578251825591602001919060010190610a8d565b5b509050610ab69190610aba565b5090565b5b80821115610ad3576000816000905550600101610abb565b5090565b6000610aea610ae584610f0d565b610ee8565b905082815260208101848484011115610b0657610b0561115a565b5b610b11848285610fb0565b509392505050565b600081359050610b2881611293565b92915050565b600082601f830112610b4357610b42611155565b5b8135610b53848260208601610ad7565b91505092915050565b600081359050610b6b816112aa565b92915050565b600081359050610b80816112c1565b92915050565b600060208284031215610b9c57610b9b611164565b5b600082013567ffffffffffffffff811115610bba57610bb961115f565b5b610bc684828501610b2e565b91505092915050565b60008060008060808587031215610be957610be8611164565b5b600085013567ffffffffffffffff811115610c0757610c0661115f565b5b610c1387828801610b2e565b9450506020610c2487828801610b5c565b9350506040610c3587828801610b71565b9250506060610c4687828801610b19565b91505092959194509250565b600060208284031215610c6857610c67611164565b5b6000610c7684828501610b71565b91505092915050565b610c8881610f5a565b82525050565b610c9781610f6c565b82525050565b6000610ca882610f3e565b610cb28185610f49565b9350610cc2818560208601610fbf565b610ccb81611169565b840191505092915050565b6000610ce3602283610f49565b9150610cee8261117a565b604082019050919050565b6000610d06601983610f49565b9150610d11826111c9565b602082019050919050565b6000610d29601283610f49565b9150610d34826111f2565b602082019050919050565b6000610d4c603383610f49565b9150610d578261121b565b604082019050919050565b6000610d6f601683610f49565b9150610d7a8261126a565b602082019050919050565b610d8e81610f78565b82525050565b610d9d81610fa6565b82525050565b60006020820190508181036000830152610dbd8184610c9d565b905092915050565b60006020820190508181036000830152610dde81610cd6565b9050919050565b60006020820190508181036000830152610dfe81610cf9565b9050919050565b60006020820190508181036000830152610e1e81610d1c565b9050919050565b60006020820190508181036000830152610e3e81610d3f565b9050919050565b60006020820190508181036000830152610e5e81610d62565b9050919050565b6000602082019050610e7a6000830184610d94565b92915050565b600060c082019050610e956000830189610d94565b8181036020830152610ea78188610c9d565b9050610eb66040830187610d85565b610ec36060830186610d94565b610ed06080830185610c8e565b610edd60a0830184610c7f565b979650505050505050565b6000610ef2610f03565b9050610efe828261104e565b919050565b6000604051905090565b600067ffffffffffffffff821115610f2857610f27611126565b5b610f3182611169565b9050602081019050919050565b600081519050919050565b600082825260208201905092915050565b6000610f6582610f86565b9050919050565b60008115159050919050565b600061ffff82169050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b82818337600083830152505050565b60005b83811015610fdd578082015181840152602081019050610fc2565b83811115610fec576000848401525b50505050565b6000610ffd82610f78565b91506000821415611011576110106110c8565b5b600182039050919050565b6000600282049050600182168061103457607f821691505b60208210811415611048576110476110f7565b5b50919050565b61105782611169565b810181811067ffffffffffffffff8211171561107657611075611126565b5b80604052505050565b600061108a82610fa6565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8214156110bd576110bc6110c8565b5b600182019050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b600080fd5b600080fd5b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f506172616d657472692064656c6c276576656e746f206e6f6e2061646567756160008201527f7469000000000000000000000000000000000000000000000000000000000000602082015250565b7f49642064656c6c276576656e746f206e6f6e2076616c69646f00000000000000600082015250565b7f4269676c69657474692065736175726974690000000000000000000000000000600082015250565b7f4e6f6e2070756f69207072656e6f746172652c2073656920696c2070726f707260008201527f6965746172696f2064656c6c276576656e746f00000000000000000000000000602082015250565b7f44656e61726f206e6f6e2073756666696369656e746500000000000000000000600082015250565b61129c81610f5a565b81146112a757600080fd5b50565b6112b381610f78565b81146112be57600080fd5b50565b6112ca81610fa6565b81146112d557600080fd5b5056fea26469706673582212200b30d7ae7c13bc735f1482b12c6cd08de11319806af551b3531927566dcfa18064736f6c63430008060033";

    public static final String FUNC_CREAEVENTO = "creaEvento";

    public static final String FUNC_EVENTCOUNT = "eventCount";

    public static final String FUNC_EVENTI = "eventi";

    public static final String FUNC_GET = "get";

    public static final String FUNC_NOME = "nome";

    public static final String FUNC_PRENOTAEVENTO = "prenotaEvento";

    public static final String FUNC_SET = "set";

    public static final Event CREAEVENTO_EVENT = new Event("CreaEvento", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint16>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event PRENOTAEVENTO_EVENT = new Event("PrenotaEvento", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint16>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Address>() {}));
    ;

    @Deprecated
    protected EventContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected EventContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected EventContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected EventContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<CreaEventoEventResponse> getCreaEventoEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CREAEVENTO_EVENT, transactionReceipt);
        ArrayList<CreaEventoEventResponse> responses = new ArrayList<CreaEventoEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CreaEventoEventResponse typedResponse = new CreaEventoEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.titolo = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.posti_totali = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.prezzo = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.biglietti_esauriti = (Boolean) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.organizzatore = (String) eventValues.getNonIndexedValues().get(5).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CreaEventoEventResponse> creaEventoEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CreaEventoEventResponse>() {
            @Override
            public CreaEventoEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CREAEVENTO_EVENT, log);
                CreaEventoEventResponse typedResponse = new CreaEventoEventResponse();
                typedResponse.log = log;
                typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.titolo = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.posti_totali = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.prezzo = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.biglietti_esauriti = (Boolean) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.organizzatore = (String) eventValues.getNonIndexedValues().get(5).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CreaEventoEventResponse> creaEventoEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CREAEVENTO_EVENT));
        return creaEventoEventFlowable(filter);
    }

    public List<PrenotaEventoEventResponse> getPrenotaEventoEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PRENOTAEVENTO_EVENT, transactionReceipt);
        ArrayList<PrenotaEventoEventResponse> responses = new ArrayList<PrenotaEventoEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PrenotaEventoEventResponse typedResponse = new PrenotaEventoEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.titolo = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.posti_totali = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.prezzo = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.biglietti_esauriti = (Boolean) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.organizzatore = (String) eventValues.getNonIndexedValues().get(5).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PrenotaEventoEventResponse> prenotaEventoEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PrenotaEventoEventResponse>() {
            @Override
            public PrenotaEventoEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PRENOTAEVENTO_EVENT, log);
                PrenotaEventoEventResponse typedResponse = new PrenotaEventoEventResponse();
                typedResponse.log = log;
                typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.titolo = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.posti_totali = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.prezzo = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.biglietti_esauriti = (Boolean) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.organizzatore = (String) eventValues.getNonIndexedValues().get(5).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PrenotaEventoEventResponse> prenotaEventoEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PRENOTAEVENTO_EVENT));
        return prenotaEventoEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> creaEvento(String titolo, BigInteger posti_totali, BigInteger prezzo, String organizzatore) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CREAEVENTO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(titolo), 
                new org.web3j.abi.datatypes.generated.Uint16(posti_totali), 
                new org.web3j.abi.datatypes.generated.Uint256(prezzo), 
                new org.web3j.abi.datatypes.Address(160, organizzatore)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> eventCount() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_EVENTCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple6<BigInteger, String, BigInteger, BigInteger, Boolean, String>> eventi(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_EVENTI, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint16>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Address>() {}));
        return new RemoteFunctionCall<Tuple6<BigInteger, String, BigInteger, BigInteger, Boolean, String>>(function,
                new Callable<Tuple6<BigInteger, String, BigInteger, BigInteger, Boolean, String>>() {
                    @Override
                    public Tuple6<BigInteger, String, BigInteger, BigInteger, Boolean, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<BigInteger, String, BigInteger, BigInteger, Boolean, String>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (Boolean) results.get(4).getValue(), 
                                (String) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<String> get() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GET, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> nome() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NOME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> prenotaEvento(BigInteger _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PRENOTAEVENTO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> set(String _nome) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_nome)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static EventContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new EventContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static EventContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new EventContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static EventContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new EventContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static EventContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EventContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<EventContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EventContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<EventContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EventContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EventContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EventContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EventContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EventContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class CreaEventoEventResponse extends BaseEventResponse {
        public BigInteger id;

        public String titolo;

        public BigInteger posti_totali;

        public BigInteger prezzo;

        public Boolean biglietti_esauriti;

        public String organizzatore;
    }

    public static class PrenotaEventoEventResponse extends BaseEventResponse {
        public BigInteger id;

        public String titolo;

        public BigInteger posti_totali;

        public BigInteger prezzo;

        public Boolean biglietti_esauriti;

        public String organizzatore;
    }
}
