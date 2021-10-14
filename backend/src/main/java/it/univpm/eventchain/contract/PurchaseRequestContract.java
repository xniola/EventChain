package it.univpm.eventchain.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
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
public class PurchaseRequestContract extends Contract {
    public static final String BINARY = "60806040523480156200001157600080fd5b5060405162001c6038038062001c60833981810160405281019062000037919062000410565b8060008151116200007f576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040162000076906200049d565b60405180910390fd5b336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060008390508073ffffffffffffffffffffffffffffffffffffffff1663eb44adce6040518163ffffffff1660e01b815260040160206040518083038186803b1580156200010b57600080fd5b505afa15801562000120573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190620001469190620003de565b600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508073ffffffffffffffffffffffffffffffffffffffff1663c74d97cd6040518163ffffffff1660e01b815260040160206040518083038186803b158015620001cd57600080fd5b505afa158015620001e2573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190620002089190620003de565b600260006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555082600390805190602001906200026092919062000299565b506000600760006101000a81548160ff021916908360028111156200028a576200028962000605565b5b021790555050505050620006fa565b828054620002a79062000599565b90600052602060002090601f016020900481019282620002cb576000855562000317565b82601f10620002e657805160ff191683800117855562000317565b8280016001018555821562000317579182015b8281111562000316578251825591602001919060010190620002f9565b5b5090506200032691906200032a565b5090565b5b80821115620003455760008160009055506001016200032b565b5090565b6000620003606200035a84620004e8565b620004bf565b9050828152602081018484840111156200037f576200037e62000697565b5b6200038c84828562000563565b509392505050565b600081519050620003a581620006e0565b92915050565b600082601f830112620003c357620003c262000692565b5b8151620003d584826020860162000349565b91505092915050565b600060208284031215620003f757620003f6620006a1565b5b6000620004078482850162000394565b91505092915050565b600080604083850312156200042a5762000429620006a1565b5b60006200043a8582860162000394565b925050602083015167ffffffffffffffff8111156200045e576200045d6200069c565b5b6200046c85828601620003ab565b9150509250929050565b6000620004856011836200051e565b91506200049282620006b7565b602082019050919050565b60006020820190508181036000830152620004b88162000476565b9050919050565b6000620004cb620004de565b9050620004d98282620005cf565b919050565b6000604051905090565b600067ffffffffffffffff82111562000506576200050562000663565b5b6200051182620006a6565b9050602081019050919050565b600082825260208201905092915050565b60006200053c8262000543565b9050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60005b838110156200058357808201518184015260208101905062000566565b8381111562000593576000848401525b50505050565b60006002820490506001821680620005b257607f821691505b60208210811415620005c957620005c862000634565b5b50919050565b620005da82620006a6565b810181811067ffffffffffffffff82111715620005fc57620005fb62000663565b5b80604052505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602160045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b600080fd5b600080fd5b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e6f742076616c696420737472696e6721000000000000000000000000000000600082015250565b620006eb816200052f565b8114620006f757600080fd5b50565b611556806200070a6000396000f3fe608060405234801561001057600080fd5b50600436106100ce5760003560e01c806355d1723a1161008c5780637a7f01a7116100665780637a7f01a7146101f1578063c70d4f431461020f578063e604cc891461022b578063fc39ca1b14610249576100ce565b806355d1723a146101855780637284e416146101a357806373c05762146101c1576100ce565b80626d6cae146100d357806303a40a02146100f157806317d70f7c1461010f578063200d2ed21461012d578063452fd6211461014b57806349e894d814610155575b600080fd5b6100db610253565b6040516100e8919061108b565b60405180910390f35b6100f9610259565b6040516101069190610f98565b60405180910390f35b61011761027f565b604051610124919061108b565b60405180910390f35b610135610285565b6040516101429190610fce565b60405180910390f35b610153610298565b005b61016f600480360381019061016a9190610d83565b6104a0565b60405161017c9190610fe9565b60405180910390f35b61018d6104df565b60405161019a9190610f98565b60405180910390f35b6101ab610503565b6040516101b89190610fe9565b60405180910390f35b6101db60048036038101906101d69190610d56565b610591565b6040516101e89190610fb3565b60405180910390f35b6101f96107f1565b6040516102069190610fe9565b60405180910390f35b61022960048036038101906102249190610db0565b61087f565b005b610233610a61565b6040516102409190610f98565b60405180910390f35b610251610a87565b005b60055481565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60065481565b600760009054906101000a900460ff1681565b600060028111156102ac576102ab611348565b5b600760009054906101000a900460ff1660028111156102ce576102cd611348565b5b1461030e576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016103059061100b565b60405180910390fd5b6000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508073ffffffffffffffffffffffffffffffffffffffff16637237a4b6336040518263ffffffff1660e01b815260040161036e9190610f98565b602060405180830381600087803b15801561038857600080fd5b505af115801561039c573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906103c09190610df9565b6006819055508073ffffffffffffffffffffffffffffffffffffffff1663ee8e03276040518163ffffffff1660e01b8152600401600060405180830381600087803b15801561040e57600080fd5b505af1158015610422573d6000803e3d6000fd5b505050506040518060400160405280601081526020017f4e465469636b657420437265617465640000000000000000000000000000000081525060049080519060200190610471929190610c04565b506002600760006101000a81548160ff0219169083600281111561049857610497611348565b5b021790555050565b60606104ab82610591565b6104b8608084901b610591565b6040516020016104c9929190610f61565b6040516020818303038152906040529050919050565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60038054610510906112ac565b80601f016020809104026020016040519081016040528092919081815260200182805461053c906112ac565b80156105895780601f1061055e57610100808354040283529160200191610589565b820191906000526020600020905b81548152906001019060200180831161056c57829003601f168201915b505050505081565b6000604077ffffffffffffffff0000000000000000000000000000000060001b836fffffffffffffffffffffffffffffffff191616901c7fffffffffffffffff00000000000000000000000000000000000000000000000060001b836fffffffffffffffffffffffffffffffff19161617905060207bffffffff000000000000000000000000ffffffff000000000000000060001b8216901c7fffffffff000000000000000000000000ffffffff00000000000000000000000060001b821617905060107dffff000000000000ffff000000000000ffff000000000000ffff0000000060001b8216901c7fffff000000000000ffff000000000000ffff000000000000ffff00000000000060001b821617905060087eff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000060001b8216901c7fff000000ff000000ff000000ff000000ff000000ff000000ff000000ff00000060001b821617905060087f0f000f000f000f000f000f000f000f000f000f000f000f000f000f000f000f0060001b8216901c60047ff000f000f000f000f000f000f000f000f000f000f000f000f000f000f000f00060001b8316901c17905060077f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f60047f06060606060606060606060606060606060606060606060606060606060606068460001c6107a19190611123565b901c166107ae9190611179565b8160001c7f30303030303030303030303030303030303030303030303030303030303030306107dd9190611123565b6107e79190611123565b60001b9050919050565b600480546107fe906112ac565b80601f016020809104026020016040519081016040528092919081815260200182805461082a906112ac565b80156108775780601f1061084c57610100808354040283529160200191610877565b820191906000526020600020905b81548152906001019060200180831161085a57829003601f168201915b505050505081565b8060008151116108c4576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016108bb9061106b565b60405180910390fd5b3373ffffffffffffffffffffffffffffffffffffffff16600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161480156109545750600060028111156109305761092f611348565b5b600760009054906101000a900460ff16600281111561095257610951611348565b5b145b610993576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161098a9061102b565b60405180910390fd5b6000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508073ffffffffffffffffffffffffffffffffffffffff1663ee8e03276040518163ffffffff1660e01b8152600401600060405180830381600087803b158015610a0257600080fd5b505af1158015610a16573d6000803e3d6000fd5b505050508260049080519060200190610a30929190610c04565b506001600760006101000a81548160ff02191690836002811115610a5757610a56611348565b5b0217905550505050565b600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b3373ffffffffffffffffffffffffffffffffffffffff1660008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16148015610b15575060006002811115610af157610af0611348565b5b600760009054906101000a900460ff166002811115610b1357610b12611348565b5b145b610b54576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610b4b9061104b565b60405180910390fd5b6000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508073ffffffffffffffffffffffffffffffffffffffff1663dcc82edf6040518163ffffffff1660e01b8152600401602060405180830381600087803b158015610bc357600080fd5b505af1158015610bd7573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610bfb9190610df9565b60058190555050565b828054610c10906112ac565b90600052602060002090601f016020900481019282610c325760008555610c79565b82601f10610c4b57805160ff1916838001178555610c79565b82800160010185558215610c79579182015b82811115610c78578251825591602001919060010190610c5d565b5b509050610c869190610c8a565b5090565b5b80821115610ca3576000816000905550600101610c8b565b5090565b6000610cba610cb5846110cb565b6110a6565b905082815260208101848484011115610cd657610cd56113da565b5b610ce184828561126a565b509392505050565b600081359050610cf8816114db565b92915050565b600081359050610d0d816114f2565b92915050565b600082601f830112610d2857610d276113d5565b5b8135610d38848260208601610ca7565b91505092915050565b600081519050610d5081611509565b92915050565b600060208284031215610d6c57610d6b6113e4565b5b6000610d7a84828501610ce9565b91505092915050565b600060208284031215610d9957610d986113e4565b5b6000610da784828501610cfe565b91505092915050565b600060208284031215610dc657610dc56113e4565b5b600082013567ffffffffffffffff811115610de457610de36113df565b5b610df084828501610d13565b91505092915050565b600060208284031215610e0f57610e0e6113e4565b5b6000610e1d84828501610d41565b91505092915050565b610e2f816111d3565b82525050565b610e3e81611211565b82525050565b610e55610e5082611211565b61130f565b82525050565b610e6481611258565b82525050565b6000610e75826110fc565b610e7f8185611107565b9350610e8f818560208601611279565b610e98816113e9565b840191505092915050565b6000610eb0601883611107565b9150610ebb826113fa565b602082019050919050565b6000610ed3601883611107565b9150610ede82611423565b602082019050919050565b6000610ef6600283611118565b9150610f018261144c565b600282019050919050565b6000610f19601583611107565b9150610f2482611475565b602082019050919050565b6000610f3c601183611107565b9150610f478261149e565b602082019050919050565b610f5b8161124e565b82525050565b6000610f6c82610ee9565b9150610f788285610e44565b602082019150610f888284610e44565b6020820191508190509392505050565b6000602082019050610fad6000830184610e26565b92915050565b6000602082019050610fc86000830184610e35565b92915050565b6000602082019050610fe36000830184610e5b565b92915050565b600060208201905081810360008301526110038184610e6a565b905092915050565b6000602082019050818103600083015261102481610ea3565b9050919050565b6000602082019050818103600083015261104481610ec6565b9050919050565b6000602082019050818103600083015261106481610f0c565b9050919050565b6000602082019050818103600083015261108481610f2f565b9050919050565b60006020820190506110a06000830184610f52565b92915050565b60006110b06110c1565b90506110bc82826112de565b919050565b6000604051905090565b600067ffffffffffffffff8211156110e6576110e56113a6565b5b6110ef826113e9565b9050602081019050919050565b600081519050919050565b600082825260208201905092915050565b600081905092915050565b600061112e8261124e565b91506111398361124e565b9250827fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff0382111561116e5761116d611319565b5b828201905092915050565b60006111848261124e565b915061118f8361124e565b9250817fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff04831182151516156111c8576111c7611319565b5b828202905092915050565b60006111de8261122e565b9050919050565b60007fffffffffffffffffffffffffffffffff0000000000000000000000000000000082169050919050565b6000819050919050565b6000819050611229826114c7565b919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b60006112638261121b565b9050919050565b82818337600083830152505050565b60005b8381101561129757808201518184015260208101905061127c565b838111156112a6576000848401525b50505050565b600060028204905060018216806112c457607f821691505b602082108114156112d8576112d7611377565b5b50919050565b6112e7826113e9565b810181811067ffffffffffffffff82111715611306576113056113a6565b5b80604052505050565b6000819050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602160045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b600080fd5b600080fd5b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e6f742076616c69642073756363657373526571756573740000000000000000600082015250565b7f4e6f742076616c6964206661696c656452657175657374210000000000000000600082015250565b7f3078000000000000000000000000000000000000000000000000000000000000600082015250565b7f4e6f742076616c69642061646452657175657374210000000000000000000000600082015250565b7f4e6f742076616c696420737472696e6721000000000000000000000000000000600082015250565b600381106114d8576114d7611348565b5b50565b6114e4816111e5565b81146114ef57600080fd5b50565b6114fb81611211565b811461150657600080fd5b50565b6115128161124e565b811461151d57600080fd5b5056fea2646970667358221220ee6fce4f26d843351818fa20a7399c8014a050724bbc9308a32bfd5d4203236e64736f6c63430008060033";

    public static final String FUNC_ADDREQUEST = "addRequest";

    public static final String FUNC_DESCRIPTION = "description";

    public static final String FUNC_FAILEDREQUEST = "failedRequest";

    public static final String FUNC_REQUESTID = "requestId";

    public static final String FUNC_RESPONSE = "response";

    public static final String FUNC_STATUS = "status";

    public static final String FUNC_SUCCESSREQUEST = "successRequest";

    public static final String FUNC_TICKETBUYER = "ticketBuyer";

    public static final String FUNC_TICKETEVENT = "ticketEvent";

    public static final String FUNC_TICKETRESELLER = "ticketReseller";

    public static final String FUNC_TOHEX = "toHex";

    public static final String FUNC_TOHEX16 = "toHex16";

    public static final String FUNC_TOKENID = "tokenId";

    @Deprecated
    protected PurchaseRequestContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PurchaseRequestContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PurchaseRequestContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PurchaseRequestContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> addRequest() {
        final Function function = new Function(
                FUNC_ADDREQUEST, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> description() {
        final Function function = new Function(FUNC_DESCRIPTION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> failedRequest(String _response) {
        final Function function = new Function(
                FUNC_FAILEDREQUEST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_response)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> requestId() {
        final Function function = new Function(FUNC_REQUESTID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> response() {
        final Function function = new Function(FUNC_RESPONSE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> status() {
        final Function function = new Function(FUNC_STATUS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> successRequest() {
        final Function function = new Function(
                FUNC_SUCCESSREQUEST, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> ticketBuyer() {
        final Function function = new Function(FUNC_TICKETBUYER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> ticketEvent() {
        final Function function = new Function(FUNC_TICKETEVENT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> ticketReseller() {
        final Function function = new Function(FUNC_TICKETRESELLER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> toHex(byte[] data) {
        final Function function = new Function(FUNC_TOHEX, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(data)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<byte[]> toHex16(byte[] data) {
        final Function function = new Function(FUNC_TOHEX16, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes16(data)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<BigInteger> tokenId() {
        final Function function = new Function(FUNC_TOKENID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static PurchaseRequestContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PurchaseRequestContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PurchaseRequestContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PurchaseRequestContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PurchaseRequestContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new PurchaseRequestContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PurchaseRequestContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PurchaseRequestContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PurchaseRequestContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _eventReseller, String _description) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _eventReseller), 
                new org.web3j.abi.datatypes.Utf8String(_description)));
        return deployRemoteCall(PurchaseRequestContract.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<PurchaseRequestContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _eventReseller, String _description) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _eventReseller), 
                new org.web3j.abi.datatypes.Utf8String(_description)));
        return deployRemoteCall(PurchaseRequestContract.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<PurchaseRequestContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _eventReseller, String _description) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _eventReseller), 
                new org.web3j.abi.datatypes.Utf8String(_description)));
        return deployRemoteCall(PurchaseRequestContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<PurchaseRequestContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _eventReseller, String _description) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _eventReseller), 
                new org.web3j.abi.datatypes.Utf8String(_description)));
        return deployRemoteCall(PurchaseRequestContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}