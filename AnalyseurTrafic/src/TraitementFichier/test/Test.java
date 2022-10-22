package TraitementFichier.test;

import java.io.FileNotFoundException;

public @interface Test {

    Class<FileNotFoundException> expected();

}
