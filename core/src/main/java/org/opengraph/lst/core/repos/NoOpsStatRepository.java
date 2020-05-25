package org.opengraph.lst.core.repos;

import java.util.List;

import org.opengraph.lst.core.beans.Stat;
import org.opengraph.lst.core.exceptions.NotImplmentedException;
import org.springframework.stereotype.Repository;

@Repository
public class NoOpsStatRepository implements StatRepository{

	@Override
	public void save(Stat stat) {
		throw new NotImplmentedException();
		
	}

	@Override
	public List<Stat> get(String id) {
		throw new NotImplmentedException();
	}

	@Override
	public List<Stat> get() {
		throw new NotImplmentedException();
	}

}
