package org.jboss.as.quickstarts.entity;


import javax.annotation.PostConstruct;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import javax.inject.Named;
import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;


// The @Stateful annotation eliminates the need for manual transaction demarcation
@Stateful
// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://www.cdi-spec.org/faq/#accordion6
@Model
public class RegistrationService {

    //@Inject
    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    @Inject
    private Event<Member> memberEventSrc;

    private Member newMember;

    @Produces
    @Named
    public Member getNewMember() {

        return newMember;

    }

    public void register(Member newMember) throws Exception {

        try {
            em.persist(newMember);
            memberEventSrc.fire(newMember);
        } catch (Exception e) {
            Throwable t = e;
            while ((t.getCause()) != null) {
                t = t.getCause();
            }
           throw ((Exception) t);
        }

    }

    @PostConstruct
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void initNewMember() {
        newMember = new Member();
       
    }
}