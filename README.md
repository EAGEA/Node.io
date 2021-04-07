## Limits

* The game is not fair, the _host_ is advantaged: when sending an `Action` every player should wait 
  for _host_ validation to update their model (_i.e. to receive back an `Action` from the host_),
  while the _host_ sees her/his model updated directly during the validation process. Thus there is
  no delay for the _host_.
  To avoid this we could update the model of the _host_ by also sending validation messages to 
  her/him like every other player. But during this time lapse, _host_ could validate actions from
  other player with a non-updated model. This could lead to collisions. To avoid this we could add
  a **Lamport timestamp** system, and queue the `Actions` received to early on the _host_ side.
  
## Attributions

* Background made by **[Enjl](https://enjl.itch.io/)**.

* Tiles made by **[Ajay Karat | Devil's Work.shop](https://www.devilswork.shop/)**.

* Character made by **[Gamekrazzy](https://gamekrazzy.itch.io/)**.