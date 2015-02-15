package ScEventPlayer

/**
 * Created by steve on 2/8/15.
 */
abstract class PlayEvent[TModel <: Aggregate[TModel]] (val id: AggregateId[TModel], val version: Int){
  def aggregateId = id.idVal

  def ApplyTo(model: TModel): Unit = {
    ApplyChangesTo(model)
    model.latestVersion = version
  }

  protected def ApplyChangesTo(model: TModel)
}
