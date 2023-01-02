package br.edu.ifpb.dac.falacampus.presentation.control;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.dac.falacampus.business.service.AnswerConverterService;
import br.edu.ifpb.dac.falacampus.business.service.AnswerService;
import br.edu.ifpb.dac.falacampus.business.service.CommentService;
import br.edu.ifpb.dac.falacampus.business.service.DetailsCommentConverterService;
import br.edu.ifpb.dac.falacampus.business.service.UserService;

import br.edu.ifpb.dac.falacampus.exceptions.BadArgumentsException;
import br.edu.ifpb.dac.falacampus.exceptions.CommentSolvedException;
import br.edu.ifpb.dac.falacampus.exceptions.InternalException;
import br.edu.ifpb.dac.falacampus.exceptions.ResourceNotFoundException;
import br.edu.ifpb.dac.falacampus.model.entity.Answer;
import br.edu.ifpb.dac.falacampus.model.entity.Comment;
import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.model.enums.StatusComment;
import br.edu.ifpb.dac.falacampus.presentation.dto.AnswerDto;
import br.edu.ifpb.dac.falacampus.presentation.dto.DetailsCommentDto;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {
	
	@Autowired
	private AnswerConverterService answerConverterService;
	
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userCrudService;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private DetailsCommentConverterService detailsCommentConverterService;
	
	@PostMapping
	public ResponseEntity save(@RequestBody @Valid AnswerDto dto) {
		try {
			if (dto.getCommentId() == null) {
				throw new IllegalStateException("commentId cannot be null");
			}
			
			Long commentId = dto.getCommentId();
			Comment comment = commentService.findById(commentId);
						
			if(comment == null) {
				throw new IllegalStateException(String.format("Cound not find any comment with id=%1", commentId));
			}
			
			Answer entity = answerConverterService.dtoToAnswer(dto);
			
			if (comment.getStatusComment()== StatusComment.SOLVED) {
				
				throw new CommentSolvedException("Comment is solved.");
				
			} else {
				comment.setStatusComment(StatusComment.SOLVED);
				comment.setAnswer(entity);
				
				entity.setComment(comment);
				entity = answerService.save(entity);
				dto = answerConverterService.answerToDTO(entity);
				
				return new ResponseEntity(dto, HttpStatus.CREATED);
			}
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	//=====
		@PutMapping("{id}")
		public ResponseEntity returned(@PathVariable("id") Long id) {

			try {
//				Comment enti = commentService.findById(id);
//				
//				Comment entity = detailsCommentConverterService.dtoToDetailsComment(dto); 			
			Comment entity=commentService.findById(id);
			
				entity.setStatusComment(StatusComment.RETURNED);
				entity = commentService.update(entity);
				
				
				DetailsCommentDto dto = detailsCommentConverterService.detailsCommentToDTO(entity); 

				return ResponseEntity.ok(dto);
			} catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}

		}


		//====

	
//	@PutMapping("{id}")
//	public ResponseEntity update(@PathVariable("id") Long id, @RequestBody @Valid AnswerDto dto) {
//		try {
//			dto.setId(id);
//			
//			Long commentId = dto.getCommentId();
//			Comment comment = commentService.findById(commentId);
//			
//			if(comment == null) {
//				throw new IllegalStateException(String.format("Could not find any comment with id=%1", id));
//			}
//			
//			Answer entity = answerConverterService.dtoToAnswer(dto);
//			entity.setComment(comment);
//			entity = answerService.update(entity);
//			dto = answerConverterService.answerToDTO(entity);
//			
//			return ResponseEntity.ok(dto);
//		} catch(Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}
	
	@DeleteMapping("{id}")
	public ResponseEntity delete(@PathVariable("id") Long id) {
		try {
			answerService.deleteById(id);
			
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		} catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping
	public ResponseEntity findByFilter (
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "message", required = false) String message,
			@RequestParam(value = "commentId", required = false) Long commentId,
			@RequestParam(value = "creationDate", required = false) LocalDateTime creationDate,
			@RequestParam(value = "authorId", required = false) Long authorId) {
		
			try {
			
			Answer filter = new Answer();
			
			filter.setId(id);  
			filter.setMessage(message);  
			filter.setCreationDate(creationDate); 
			
			Comment comment = commentService.findById(commentId);
			User author = userCrudService.findById(authorId);
			
			if(comment == null) {
				throw new IllegalStateException(String.format("Cound not find any comment whit id=%1", commentId));
			}
			
			if (author == null) {
				throw new IllegalStateException(String.format("Cound not find any author whit id=%1", authorId));
			}
			
			filter.setComment(comment);
			filter.setAuthor(author);
			
			List<Answer> entities = answerService.find(filter);
			List<AnswerDto> dtos = answerConverterService.answerToDTOList(entities);
			
			return ResponseEntity.ok(dtos);
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	private AnswerDto mapToAnswerDto(Answer answer) {
		return mapper.map(answer, AnswerDto.class);
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> findAll() throws Exception {

		List<AnswerDto> dtos = answerService.findAll().stream().map(this::mapToAnswerDto).toList();

		return ResponseEntity.ok(dtos);
	}
	
	
	@GetMapping("/{id}")
	public Answer findById(@PathVariable("id") Long id) throws Exception {

		Answer result = answerService.findById(id);

		if (result == null){
			throw new Exception("answer not exist!");

		} else {
			return result;	
		}
	}
	
	
//	@GetMapping("/{id}")
//	public ResponseEntity findById(@PathVariable Long id) throws Exception{
//		
//		try {
//			Answer entity = answerService.findById(id);
//			AnswerDto dto = answerConverterService.answerToDTO(entity);
//			
//			
//			return ResponseEntity.ok(dto);
//		
//		} catch(Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}	
	
	
}
