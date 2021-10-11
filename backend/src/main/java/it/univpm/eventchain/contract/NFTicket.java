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
public class NFTicket extends Contract {
    public static final String BINARY = "60806040523480156200001157600080fd5b50604051620015aa380380620015aa833981810160405281019062000037919062000467565b82600081116200007e576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016200007590620005c1565b60405180910390fd5b826000815111620000c6576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401620000bd9062000605565b60405180910390fd5b8560008151116200010e576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401620001059062000605565b60405180910390fd5b876000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506200015e620001be60201b60201c565b8560018190555084600290805190602001906200017d929190620002d9565b50866003908051906020019062000196929190620002d9565b508360049080519060200190620001af929190620002d9565b505050505050505050620008a9565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690503373ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1663e604cc896040518163ffffffff1660e01b815260040160206040518083038186803b1580156200024257600080fd5b505afa15801562000257573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906200027d919062000435565b73ffffffffffffffffffffffffffffffffffffffff1614620002d6576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401620002cd90620005e3565b60405180910390fd5b50565b828054620002e7906200070b565b90600052602060002090601f0160209004810192826200030b576000855562000357565b82601f106200032657805160ff191683800117855562000357565b8280016001018555821562000357579182015b828111156200035657825182559160200191906001019062000339565b5b5090506200036691906200036a565b5090565b5b80821115620003855760008160009055506001016200036b565b5090565b6000620003a06200039a8462000650565b62000627565b905082815260208101848484011115620003bf57620003be620007da565b5b620003cc848285620006d5565b509392505050565b600081519050620003e58162000875565b92915050565b600082601f830112620004035762000402620007d5565b5b81516200041584826020860162000389565b91505092915050565b6000815190506200042f816200088f565b92915050565b6000602082840312156200044e576200044d620007e4565b5b60006200045e84828501620003d4565b91505092915050565b600080600080600060a08688031215620004865762000485620007e4565b5b60006200049688828901620003d4565b955050602086015167ffffffffffffffff811115620004ba57620004b9620007df565b5b620004c888828901620003eb565b9450506040620004db888289016200041e565b935050606086015167ffffffffffffffff811115620004ff57620004fe620007df565b5b6200050d88828901620003eb565b925050608086015167ffffffffffffffff811115620005315762000530620007df565b5b6200053f88828901620003eb565b9150509295509295909350565b60006200055b60128362000686565b91506200056882620007fa565b602082019050919050565b60006200058260198362000686565b91506200058f8262000823565b602082019050919050565b6000620005a960118362000686565b9150620005b6826200084c565b602082019050919050565b60006020820190508181036000830152620005dc816200054c565b9050919050565b60006020820190508181036000830152620005fe8162000573565b9050919050565b6000602082019050818103600083015262000620816200059a565b9050919050565b60006200063362000646565b905062000641828262000741565b919050565b6000604051905090565b600067ffffffffffffffff8211156200066e576200066d620007a6565b5b6200067982620007e9565b9050602081019050919050565b600082825260208201905092915050565b6000620006a482620006ab565b9050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b60005b83811015620006f5578082015181840152602081019050620006d8565b8381111562000705576000848401525b50505050565b600060028204905060018216806200072457607f821691505b602082108114156200073b576200073a62000777565b5b50919050565b6200074c82620007e9565b810181811067ffffffffffffffff821117156200076e576200076d620007a6565b5b80604052505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b600080fd5b600080fd5b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e6f742076616c696420696e7465676572210000000000000000000000000000600082015250565b7f4e6f742076616c6964207469636b657420726573656c6c657200000000000000600082015250565b7f4e6f742076616c696420737472696e6721000000000000000000000000000000600082015250565b620008808162000697565b81146200088c57600080fd5b50565b6200089a81620006cb565b8114620008a657600080fd5b50565b610cf180620008b96000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c806373c057621161005b57806373c0576214610117578063a035b1fe14610147578063ca35ae5414610165578063eac989f81461016f57610088565b80630c737add1461008d5780631958a4f2146100ab5780633537271d146100c957806349e894d8146100e7575b600080fd5b61009561018d565b6040516100a2919061099b565b60405180910390f35b6100b361021b565b6040516100c0919061099b565b60405180910390f35b6100d16102a9565b6040516100de9190610965565b60405180910390f35b61010160048036038101906100fc919061083e565b6102cd565b60405161010e919061099b565b60405180910390f35b610131600480360381019061012c9190610811565b61030c565b60405161013e9190610980565b60405180910390f35b61014f61056c565b60405161015c91906109dd565b60405180910390f35b61016d610572565b005b610177610603565b604051610184919061099b565b60405180910390f35b6003805461019a90610b74565b80601f01602080910402602001604051908101604052809291908181526020018280546101c690610b74565b80156102135780601f106101e857610100808354040283529160200191610213565b820191906000526020600020905b8154815290600101906020018083116101f657829003601f168201915b505050505081565b6002805461022890610b74565b80601f016020809104026020016040519081016040528092919081815260200182805461025490610b74565b80156102a15780601f10610276576101008083540402835291602001916102a1565b820191906000526020600020905b81548152906001019060200180831161028457829003601f168201915b505050505081565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60606102d88261030c565b6102e5608084901b61030c565b6040516020016102f692919061092e565b6040516020818303038152906040529050919050565b6000604077ffffffffffffffff0000000000000000000000000000000060001b836fffffffffffffffffffffffffffffffff191616901c7fffffffffffffffff00000000000000000000000000000000000000000000000060001b836fffffffffffffffffffffffffffffffff19161617905060207bffffffff000000000000000000000000ffffffff000000000000000060001b8216901c7fffffffff000000000000000000000000ffffffff00000000000000000000000060001b821617905060107dffff000000000000ffff000000000000ffff000000000000ffff0000000060001b8216901c7fffff000000000000ffff000000000000ffff000000000000ffff00000000000060001b821617905060087eff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000060001b8216901c7fff000000ff000000ff000000ff000000ff000000ff000000ff000000ff00000060001b821617905060087f0f000f000f000f000f000f000f000f000f000f000f000f000f000f000f000f0060001b8216901c60047ff000f000f000f000f000f000f000f000f000f000f000f000f000f000f000f00060001b8316901c17905060077f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f60047f06060606060606060606060606060606060606060606060606060606060606068460001c61051c9190610a1f565b901c166105299190610a75565b8160001c7f30303030303030303030303030303030303030303030303030303030303030306105589190610a1f565b6105629190610a1f565b60001b9050919050565b60015481565b61057a610691565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508073ffffffffffffffffffffffffffffffffffffffff1663452fd6216040518163ffffffff1660e01b8152600401600060405180830381600087803b1580156105e857600080fd5b505af11580156105fc573d6000803e3d6000fd5b5050505050565b6004805461061090610b74565b80601f016020809104026020016040519081016040528092919081815260200182805461063c90610b74565b80156106895780601f1061065e57610100808354040283529160200191610689565b820191906000526020600020905b81548152906001019060200180831161066c57829003601f168201915b505050505081565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690503373ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1663e604cc896040518163ffffffff1660e01b815260040160206040518083038186803b15801561071457600080fd5b505afa158015610728573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061074c91906107e4565b73ffffffffffffffffffffffffffffffffffffffff16146107a2576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610799906109bd565b60405180910390fd5b50565b6000815190506107b481610c76565b92915050565b6000813590506107c981610c8d565b92915050565b6000813590506107de81610ca4565b92915050565b6000602082840312156107fa576107f9610c0e565b5b6000610808848285016107a5565b91505092915050565b60006020828403121561082757610826610c0e565b5b6000610835848285016107ba565b91505092915050565b60006020828403121561085457610853610c0e565b5b6000610862848285016107cf565b91505092915050565b61087481610acf565b82525050565b61088381610b0d565b82525050565b61089a61089582610b0d565b610ba6565b82525050565b60006108ab826109f8565b6108b58185610a03565b93506108c5818560208601610b41565b6108ce81610c13565b840191505092915050565b60006108e6600283610a14565b91506108f182610c24565b600282019050919050565b6000610909601983610a03565b915061091482610c4d565b602082019050919050565b61092881610b37565b82525050565b6000610939826108d9565b91506109458285610889565b6020820191506109558284610889565b6020820191508190509392505050565b600060208201905061097a600083018461086b565b92915050565b6000602082019050610995600083018461087a565b92915050565b600060208201905081810360008301526109b581846108a0565b905092915050565b600060208201905081810360008301526109d6816108fc565b9050919050565b60006020820190506109f2600083018461091f565b92915050565b600081519050919050565b600082825260208201905092915050565b600081905092915050565b6000610a2a82610b37565b9150610a3583610b37565b9250827fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff03821115610a6a57610a69610bb0565b5b828201905092915050565b6000610a8082610b37565b9150610a8b83610b37565b9250817fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff0483118215151615610ac457610ac3610bb0565b5b828202905092915050565b6000610ada82610b17565b9050919050565b60007fffffffffffffffffffffffffffffffff0000000000000000000000000000000082169050919050565b6000819050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b60005b83811015610b5f578082015181840152602081019050610b44565b83811115610b6e576000848401525b50505050565b60006002820490506001821680610b8c57607f821691505b60208210811415610ba057610b9f610bdf565b5b50919050565b6000819050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b600080fd5b6000601f19601f8301169050919050565b7f3078000000000000000000000000000000000000000000000000000000000000600082015250565b7f4e6f742076616c6964207469636b657420726573656c6c657200000000000000600082015250565b610c7f81610acf565b8114610c8a57600080fd5b50565b610c9681610ae1565b8114610ca157600080fd5b50565b610cad81610b0d565b8114610cb857600080fd5b5056fea2646970667358221220cec443d86e2a16155922c77af285987b2af15fd6ac1edb7af5d3a8bc85591aec64736f6c63430008060033";

    public static final String FUNC_ADDTICKET = "addTicket";

    public static final String FUNC_PRICE = "price";

    public static final String FUNC_PURCHASEREQUEST = "purchaseRequest";

    public static final String FUNC_TAXSEAL = "taxSeal";

    public static final String FUNC_TICKETTYPE = "ticketType";

    public static final String FUNC_TOHEX = "toHex";

    public static final String FUNC_TOHEX16 = "toHex16";

    public static final String FUNC_URI = "uri";

    @Deprecated
    protected NFTicket(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected NFTicket(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected NFTicket(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected NFTicket(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> addTicket() {
        final Function function = new Function(
                FUNC_ADDTICKET, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> price() {
        final Function function = new Function(FUNC_PRICE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> purchaseRequest() {
        final Function function = new Function(FUNC_PURCHASEREQUEST, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> taxSeal() {
        final Function function = new Function(FUNC_TAXSEAL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> ticketType() {
        final Function function = new Function(FUNC_TICKETTYPE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
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

    public RemoteFunctionCall<String> uri() {
        final Function function = new Function(FUNC_URI, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static NFTicket load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new NFTicket(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static NFTicket load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new NFTicket(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static NFTicket load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new NFTicket(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static NFTicket load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new NFTicket(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<NFTicket> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _purchaseRequest, String _type, BigInteger _price, String _taxSeal, String _uri) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _purchaseRequest), 
                new org.web3j.abi.datatypes.Utf8String(_type), 
                new org.web3j.abi.datatypes.generated.Uint256(_price), 
                new org.web3j.abi.datatypes.Utf8String(_taxSeal), 
                new org.web3j.abi.datatypes.Utf8String(_uri)));
        return deployRemoteCall(NFTicket.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<NFTicket> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _purchaseRequest, String _type, BigInteger _price, String _taxSeal, String _uri) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _purchaseRequest), 
                new org.web3j.abi.datatypes.Utf8String(_type), 
                new org.web3j.abi.datatypes.generated.Uint256(_price), 
                new org.web3j.abi.datatypes.Utf8String(_taxSeal), 
                new org.web3j.abi.datatypes.Utf8String(_uri)));
        return deployRemoteCall(NFTicket.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<NFTicket> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _purchaseRequest, String _type, BigInteger _price, String _taxSeal, String _uri) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _purchaseRequest), 
                new org.web3j.abi.datatypes.Utf8String(_type), 
                new org.web3j.abi.datatypes.generated.Uint256(_price), 
                new org.web3j.abi.datatypes.Utf8String(_taxSeal), 
                new org.web3j.abi.datatypes.Utf8String(_uri)));
        return deployRemoteCall(NFTicket.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<NFTicket> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _purchaseRequest, String _type, BigInteger _price, String _taxSeal, String _uri) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _purchaseRequest), 
                new org.web3j.abi.datatypes.Utf8String(_type), 
                new org.web3j.abi.datatypes.generated.Uint256(_price), 
                new org.web3j.abi.datatypes.Utf8String(_taxSeal), 
                new org.web3j.abi.datatypes.Utf8String(_uri)));
        return deployRemoteCall(NFTicket.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
