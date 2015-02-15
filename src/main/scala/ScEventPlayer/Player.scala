package ScEventPlayer

/**
 * Created by steve on 2/8/15.
 */
class Player[TModel <: Aggregate[TModel]] {
  def PlayFor(model: TModel, event: PlayEvent[TModel]) = {
    event.ApplyTo(model)
  }
}
