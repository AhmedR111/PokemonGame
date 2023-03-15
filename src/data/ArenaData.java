package data;

import model.*;
import util.ItemFactory;
import util.PokemonFactory;

import java.util.List;

/**
 * Class dedicated to reading data from text files and converting them into appropriate instances (i.e. Pokemon, Trainer, Item etc.)
 */
public class ArenaData extends GenericData<ArenaDto>{
    /**
     * Constructor gets initialized with the relative path to the input file
     *
     * @param dataSourceFile
     */
    public ArenaData(String dataSourceFile) {
        super(dataSourceFile);
    }

    @Override
    protected ArenaDto transformare(List<String> allLines) {

        ArenaDto arenaDto = new ArenaDto();

        boolean firstTrainer = true;
        Trainer currentTrainer = null;
        for(int i=0; i<allLines.size(); i++){
            String line = allLines.get(i);
            if(line.charAt(0) == 'T'){

                Trainer trainer = new Trainer();
                String[] elements = allLines.get(i).split("###");
                trainer.setName(elements[1]);
                trainer.setAge(Integer.valueOf(elements[2]));
                if(firstTrainer){
                    arenaDto.setTrainer1(trainer);
                    firstTrainer = false;
                }else{
                    arenaDto.setTrainer2(trainer);
                }
                currentTrainer = trainer;

            }else if(line.charAt(0) == 'P'){
                String[] elements = line.split("###");
                Pokemon pokemon = PokemonFactory.createPokemon(PokemonType.valueOf(elements[1]));
                pokemon.setTheTrainer(currentTrainer);
                currentTrainer.getPokemons().add(pokemon);
//                currentPokemon = pokemon;
            }else if(line.charAt(0) == 'I'){
                String[] elements = line.split("###");
                Item item = ItemFactory.createItem(ItemType.valueOf(elements[1]));
                currentTrainer.getItems().add(item);
            }
        }
        return arenaDto;
    }
}
