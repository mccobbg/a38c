
INSERT INTO user (user_id, first_name,last_name, email,mobile_number, password, role, created_at, updated_at)
 VALUES (lower(hex(randomblob(4))) || '-' || lower(hex(randomblob(2))) || '-4' || substr(lower(hex(randomblob(2))),2) || '-' || substr('89ab',abs(random()) % 4 + 1, 1) || substr(lower(hex(randomblob(2))),2) || '-' || lower(hex(randomblob(6))),'Happy','Days', 'happy@example.com','9876548337', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'USER', datetime('now'),  NULL);

INSERT INTO accounts (user_id, account_number, account_type, branch_address, created_at)
 VALUES (1, 186576453434, 'Savings', '123 Main Street, New York', datetime('now'));

INSERT INTO account_transactions (transaction_id, account_number, user_id, transaction_dt, transaction_summary, transaction_type, transaction_amount,
closing_balance, created_at)  VALUES (lower(hex(randomblob(4))) || '-' || lower(hex(randomblob(2))) || '-4' || substr(lower(hex(randomblob(2))),2) || '-' || substr('89ab',abs(random()) % 4 + 1, 1) || substr(lower(hex(randomblob(2))),2) || '-' || lower(hex(randomblob(6))), 186576453434, 1, datetime('now', '-7 day'), 'Coffee Shop', 'Withdrawal', 30,34500,datetime('now', '-7 day'));

INSERT INTO account_transactions (transaction_id, account_number, user_id, transaction_dt, transaction_summary, transaction_type,transaction_amount,
closing_balance, created_at)  VALUES (lower(hex(randomblob(4))) || '-' || lower(hex(randomblob(2))) || '-4' || substr(lower(hex(randomblob(2))),2) || '-' || substr('89ab',abs(random()) % 4 + 1, 1) || substr(lower(hex(randomblob(2))),2) || '-' || lower(hex(randomblob(6))), 186576453434, 1, datetime('now', '-6 day'), 'Uber', 'Withdrawal', 100,34400,datetime('now', '-6 day'));

INSERT INTO account_transactions (transaction_id, account_number, user_id, transaction_dt, transaction_summary, transaction_type,transaction_amount,
closing_balance, created_at)  VALUES (lower(hex(randomblob(4))) || '-' || lower(hex(randomblob(2))) || '-4' || substr(lower(hex(randomblob(2))),2) || '-' || substr('89ab',abs(random()) % 4 + 1, 1) || substr(lower(hex(randomblob(2))),2) || '-' || lower(hex(randomblob(6))), 186576453434, 1, datetime('now', '-5 day'), 'Self Deposit', 'Deposit', 500,34900,datetime('now', '-5 day'));

INSERT INTO account_transactions (transaction_id, account_number, user_id, transaction_dt, transaction_summary, transaction_type,transaction_amount,
closing_balance, created_at)  VALUES (lower(hex(randomblob(4))) || '-' || lower(hex(randomblob(2))) || '-4' || substr(lower(hex(randomblob(2))),2) || '-' || substr('89ab',abs(random()) % 4 + 1, 1) || substr(lower(hex(randomblob(2))),2) || '-' || lower(hex(randomblob(6))), 186576453434, 1, datetime('now', '-4 day'), 'Ebay', 'Withdrawal', 600,34300,datetime('now', '-4 day'));

INSERT INTO account_transactions (transaction_id, account_number, user_id, transaction_dt, transaction_summary, transaction_type,transaction_amount,
closing_balance, created_at)  VALUES (lower(hex(randomblob(4))) || '-' || lower(hex(randomblob(2))) || '-4' || substr(lower(hex(randomblob(2))),2) || '-' || substr('89ab',abs(random()) % 4 + 1, 1) || substr(lower(hex(randomblob(2))),2) || '-' || lower(hex(randomblob(6))), 186576453434, 1, datetime('now', '-2 day'), 'OnlineTransfer', 'Deposit', 700,35000,datetime('now', '-2 day'));

INSERT INTO account_transactions (transaction_id, account_number, user_id, transaction_dt, transaction_summary, transaction_type,transaction_amount,
closing_balance, created_at)  VALUES (lower(hex(randomblob(4))) || '-' || lower(hex(randomblob(2))) || '-4' || substr(lower(hex(randomblob(2))),2) || '-' || substr('89ab',abs(random()) % 4 + 1, 1) || substr(lower(hex(randomblob(2))),2) || '-' || lower(hex(randomblob(6))), 186576453434, 1, datetime('now', '-1 day'), 'Amazon.com', 'Withdrawal', 100,34900,datetime('now', '-1 day'));

INSERT INTO loans ( user_id, start_dt, loan_type, total_loan, amount_paid, outstanding_amount, created_at)
 VALUES ( 1, '2020-10-13', 'Home', 200000, 50000, 150000, '2020-10-13');

INSERT INTO loans ( user_id, start_dt, loan_type, total_loan, amount_paid, outstanding_amount, created_at)
 VALUES ( 1, '2020-06-06', 'Vehicle', 40000, 10000, 30000, '2020-06-06');

INSERT INTO loans ( user_id, start_dt, loan_type, total_loan, amount_paid, outstanding_amount, created_at)
 VALUES ( 1, '2018-02-14', 'Home', 50000, 10000, 40000, '2018-02-14');

INSERT INTO loans ( user_id, start_dt, loan_type, total_loan, amount_paid, outstanding_amount, created_at)
 VALUES ( 1, '2018-02-14', 'Personal', 10000, 3500, 6500, '2018-02-14');

INSERT INTO cards (card_number, user_id, card_type, total_limit, amount_used, available_amount, created_at)
 VALUES ('4565XXXX4656', 1, 'Credit', 10000, 500, 9500, datetime('now'));

INSERT INTO cards (card_number, user_id, card_type, total_limit, amount_used, available_amount, created_at)
 VALUES ('3455XXXX8673', 1, 'Credit', 7500, 600, 6900, datetime('now'));

INSERT INTO cards (card_number, user_id, card_type, total_limit, amount_used, available_amount, created_at)
 VALUES ('2359XXXX9346', 1, 'Credit', 20000, 4000, 16000, datetime('now'));

INSERT INTO notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, created_at, updated_at)
VALUES ('Home Loan Interest rates reduced', 'Home loan interest rates are reduced as per the goverment guidelines. The updated rates will be effective immediately',
datetime('now', '-30 day'), datetime('now', '+30 day'), datetime('now'), null);

INSERT INTO notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, created_at, updated_at)
VALUES ('Net Banking Offers', 'Customers who will opt for Internet banking while opening a saving account will get a $50 amazon voucher',
datetime('now', '-30 day'), datetime('now', '+30 day'), datetime('now'), null);

INSERT INTO notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, created_at, updated_at)
VALUES ('Mobile App Downtime', 'The mobile application of the EazyBank will be down from 2AM-5AM on 12/05/2020 due to maintenance activities',
datetime('now', '-30 day'), datetime('now', '+30 day'), datetime('now'), null);

INSERT INTO notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, created_at, updated_at)
VALUES ('E Auction notice', 'There will be a e-auction on 12/08/2020 on the Bank website for all the stubborn arrears.Interested parties can participate in the e-auction',
datetime('now', '-30 day'), datetime('now', '+30 day'), datetime('now'), null);

INSERT INTO notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, created_at, updated_at)
VALUES ('Launch of Millennia Cards', 'Millennia Credit Cards are launched for the premium customers of EazyBank. With these cards, you will get 5% cashback for each purchase',
datetime('now', '- 30 day'), datetime('now', '+30 day'), datetime('now'), null);

INSERT INTO notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, created_at, updated_at)
VALUES ('COVID-19 Insurance', 'EazyBank launched an insurance policy which will cover COVID-19 expenses. Please reach out to the branch for more details',
datetime('now', '-30 day'), datetime('now', '+ 30 day'), datetime('now'), null);

