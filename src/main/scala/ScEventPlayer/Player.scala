package ScEventPlayer

/**
 * Created by steve on 2/8/15.
 */
class Player[TModel <: Aggregate[TModel]] {
  def PlayFor(model: TModel, event: PlayEvent[TModel]) : Unit = {
    event.ApplyTo(model)
  }

  def PlayFor(model: TModel, events: List[PlayEvent[TModel]]) : Unit = {
    events.foreach(evt => PlayFor(model, evt))
  }
}
