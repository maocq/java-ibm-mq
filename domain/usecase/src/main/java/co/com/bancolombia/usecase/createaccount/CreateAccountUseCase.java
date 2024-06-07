package co.com.bancolombia.usecase.createaccount;

import co.com.bancolombia.model.account.Account;
import co.com.bancolombia.model.account.gateways.AccountService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateAccountUseCase {

    private final AccountService accountService;

    public Mono<Account> create(String name) {
        var account = Account.builder()
                .name(name)
                .balance(0).build();
        return accountService.createAccount(account);
    }
}
